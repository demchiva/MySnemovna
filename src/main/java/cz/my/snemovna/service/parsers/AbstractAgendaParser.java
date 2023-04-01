package cz.my.snemovna.service.parsers;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

import java.util.Comparator;
import java.util.List;

/**
 * The abstract class for agenda parser.
 */
@RequiredArgsConstructor
@Slf4j
public abstract class AbstractAgendaParser {

    /**
     * All source parsers.
     */
    private final List<SourceParser> sourceParsers;

    /**
     * The method filters parsers with same agenda source as {@link this#getAgendaSource()},
     * and parse and save agenda source data to db.
     * @param dirName agenda sources directory name.
     */
    public void parseAndSave(final String dirName) {
        LOGGER.info("Start parsing directory {} and filling database.", dirName);
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        sourceParsers
                .stream()
                .filter(e -> getAgendaSource().equals(e.getAgendaSource()))
                .sorted(Comparator.comparing(SourceParser::getPriority))
                .forEach(e -> {
                    e.delete();
                    e.parseAndSave(dirName);
                });

        stopWatch.stop();
        LOGGER.info("End parsing directory {} and filling database. Parsing took {} ms.", dirName, stopWatch.getTotalTimeMillis());
    }

    /**
     * The method get the agenda source type to filter the right parsers.
     */
    @NotNull
    protected abstract AgendaSource getAgendaSource();
}
