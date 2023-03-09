package cz.my.snemovna.service.parsers;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public abstract class AbstractAgendaParser {

    private final List<SourceParser> sourceParsers;

    public void parseAndSave(final String dirName) {
        LOGGER.info("Start parsing directory {} and filling database.", dirName);
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        sourceParsers
                .stream()
                .filter(e -> getAgendaSource().equals(e.getAgendaSource()))
                .sorted(Comparator.comparing(SourceParser::getPriority))
                .forEach(e -> e.parseAndSave(dirName));

        stopWatch.stop();
        LOGGER.info("End parsing directory {} and filling database. Parsing took {} ms.", dirName, stopWatch.getTotalTimeMillis());
    }

    @NotNull
    protected abstract AgendaSource getAgendaSource();
}
