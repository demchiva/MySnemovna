package cz.my.snemovna.service.parsers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Table;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.Metamodel;
import jakarta.validation.constraints.NotNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static cz.my.snemovna.service.loader.ArchiveUtils.BASE_DESTINATION;

@Slf4j
public abstract class AbstractSourceParser<T> implements SourceParser {

    private static final int BATCH_SIZE = 100;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    protected final String tableName;
    protected final JdbcTemplate jdbcTemplate;

    protected AbstractSourceParser(final Class<T> type,
                                   final JdbcTemplate jdbcTemplate,
                                   final EntityManager entityManager) {
        this.tableName = getTableName(entityManager, type);
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @SneakyThrows
    public void parseAndSave(@NotNull final String dirName) {
        final String sourcePath = createSourcePath(dirName, getSourceName());
        try (BufferedReader reader = new BufferedReader(new FileReader(sourcePath))) {
            List<Object[]> items = new ArrayList<>(BATCH_SIZE);
            String line = reader.readLine();
            while (line != null) {
                try(final Scanner s = new Scanner(line))  {
                    s.useDelimiter(UNL_COLUMN_DIVIDER);
                    Object[] item = convert(s.tokens().toList());

                    if (item != null) {
                        items.add(item);
                    }
                } catch (Exception e) {
                    LOGGER.error("The error occurred in file {} on line: {}. Exception: {}", sourcePath, line, e);
                }

                line = reader.readLine();

                if (items.size() >= BATCH_SIZE || (line == null && !items.isEmpty())) {
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

    // TODO implementovat smazani dat zdb pred insertem, jinak spadne na erroru
    protected void save(List<Object[]> items) {
        jdbcTemplate.batchUpdate(
                String.format(
                        "insert into %s(%s) values (%s)",
                        tableName, getColumnsOrder(), getQueryPlaceholder(items.get(0))),
                items
        );
    }

    public String getTableName(final EntityManager entityManager, final Class<T> type) {
        final Metamodel meta = entityManager.getMetamodel();
        final EntityType<T> entityType = meta.entity(type);

        final Table t = type.getAnnotation(Table.class);

        return t == null
                ? entityType.getName().toUpperCase()
                : t.name();
    }

    protected abstract String getColumnsOrder();

    protected String getQueryPlaceholder(Object[] item) {
        return "?" + ",?".repeat(Math.max(0, item.length - 1));
    }

    protected abstract Object[] convert(List<String> sourceData);

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

    protected boolean safeParseToBool(String value) {
        return "1".equals(value);
    }
}
