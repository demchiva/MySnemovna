package cz.my.snemovna.service.parsers;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static cz.my.snemovna.service.loader.ArchiveUtils.BASE_DESTINATION;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractSourceParser<T, E> implements SourceParser {

    private final static int BATCH_SIZE = 100;
    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final static DateTimeFormatter FORMATTER_HOURS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");

    protected final JpaRepository<T, E> repository;

    @Override
    @SneakyThrows
    public void parseAndSave(@NotNull final String dirName) {
        final String sourcePath = createSourcePath(dirName, getSourceName());
        try (BufferedReader reader = new BufferedReader(new FileReader(sourcePath))) {
            List<T> items = new ArrayList<>();
            String line = reader.readLine();
            while (line != null) {
                try {
                    Scanner s = new Scanner(line);
                    s.useDelimiter(UNL_COLUMN_DIVIDER);
                    T item = convert(s.tokens().toList());

                    if (item != null) {
                        items.add(item);
                    }
                } catch (Exception e) {
                    LOGGER.error("The error occurred in file {} on line: {}. Exception: {}", sourcePath, line, e);
                }

                line = reader.readLine();

                if (items.size() >= BATCH_SIZE || (line == null && items.size() > 0)) {
                    save(items);
                    items.clear();
                }
            }
        }
    }

    @Override
    public int getPriority() {
        return 1;
    }

    protected void save(List<T> items) {
        repository.saveAll(items);
    }

    protected abstract T convert(List<String> sourceData);

    protected String createSourcePath(final String dirName, final String sourceName) {
        return BASE_DESTINATION + dirName + "/" + sourceName + ".unl";
    }

    protected Long safeParseToLong(String value) {
        return value == null || value.isEmpty() ? null : Long.parseLong(value);
    }

    protected Integer safeParseToInteger(String value) {
        return value == null || value.isEmpty() ? null : Integer.parseInt(value);
    }

    protected LocalDate safeParseToLocalDate(String value) {
        return value == null || value.isEmpty() ? null : LocalDate.parse(value, FORMATTER);
    }

    protected LocalTime safeParseToLocalTime(String value) {
        return value == null || value.isEmpty() ? null : LocalTime.parse(value);
    }

    protected LocalDateTime safeParseDateWithHours(String value) {
        return value == null || value.isEmpty() ? null : LocalDateTime.parse(value, FORMATTER_HOURS);
    }

    protected boolean safeParseToBool(String value) {
        return "1".equals(value);
    }
}
