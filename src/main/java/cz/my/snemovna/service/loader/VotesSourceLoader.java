package cz.my.snemovna.service.loader;

import cz.my.snemovna.service.UrlUtils;
import cz.my.snemovna.service.parsers.votes.VotesAgendaParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * The source loader for votes agenda.
 * Agenda more complex than others, because has one file per PSP period.
 */
@Component
public class VotesSourceLoader extends AbstractSourceLoader {

    private static final String DIR_NAME = "votes_%s";
    private static final String VALUE_TO_REPLACE = "**year**";

    private final UrlUtils urlUtils;
    private final int startYear;
    private final VotesAgendaParser votesAgendaParser;

    public VotesSourceLoader(
            @Value("${snemovna.url.votes}") String dataSourceUrl,
            @Value("${snemovna.data.startYear}") int startYear,
            UrlUtils urlUtils,
            VotesAgendaParser votesAgendaParser
    ) {
        super(dataSourceUrl);
        this.urlUtils = urlUtils;
        this.startYear = startYear;
        this.votesAgendaParser = votesAgendaParser;
    }

    @Override
    public void load() {
        int currentYear = LocalDate.now().getYear();

        // Checks if url for this year exist in sources on the web  and save it in this case.
        for (int votesYear = startYear; votesYear <= currentYear; votesYear++) {
            final String votesYearString = String.valueOf(votesYear);
            final String url = dataSourceUrl.replace(VALUE_TO_REPLACE, votesYearString);
            if (urlUtils.isUrlExist(url)) {
                ArchiveUtils.unZipAndSave(url, String.format(DIR_NAME, votesYearString));
            }
        }
    }

    @Override
    public void fill() {
        int currentYear = LocalDate.now().getYear();

        // Checks if directory for this year exist in loaded sources and fills db in this case.
        for (int votesYear = startYear; votesYear <= currentYear; votesYear++) {
            final String dirName = String.format(DIR_NAME, votesYear);
            if (ArchiveUtils.isDirectoryExist(dirName)) {
                votesAgendaParser.parseAndSave(dirName);
            }
        }
    }

    @Override
    public String getDirectoryName() {
        return DIR_NAME;
    }

    @Override
    public void delete() {
        int currentYear = LocalDate.now().getYear();

        // Deletes directory for every year. In case directory does not exist deleting do nothing.
        for (int votesYear = startYear; votesYear <= currentYear; votesYear++) {
            ArchiveUtils.deleteDirectory(String.format(DIR_NAME, votesYear));
        }
    }

    @Override
    public int getPriority() {
        return 3;
    }
}
