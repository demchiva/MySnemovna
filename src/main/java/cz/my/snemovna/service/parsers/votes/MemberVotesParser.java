package cz.my.snemovna.service.parsers.votes;

import cz.my.snemovna.jpa.model.votes.MemberVotes;
import cz.my.snemovna.service.parsers.AgendaSource;
import jakarta.persistence.EntityManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class MemberVotesParser extends AbstractVoteWithYearSourceParser<MemberVotes> {

    private static final Pattern FILE_NAME_PATTERN = Pattern.compile("^hl\\d{4}h\\d\\.unl$");

    public MemberVotesParser(final JdbcTemplate jdbcTemplate, final EntityManager entityManager) {
        super(MemberVotes.class, jdbcTemplate, entityManager);
    }

    @Override
    protected String getColumnsOrder() {
        return "member_id,vote_id,result";
    }

    @Override
    protected Object[] convert(List<String> sourceData) {
        return new Object[] {
                Long.parseLong(sourceData.get(0)),
                Long.parseLong(sourceData.get(1)),
                sourceData.get(2)
        };
    }

    @Override
    public String getSourceName() {
        return "hlXXXXh1";
    }

    @Override
    public void parseAndSave(String dirName) {
        final File folder = new File(getDirectoryPath(dirName));
        final File[] listOfFiles = folder.listFiles();
        if (listOfFiles != null) {
            Arrays.stream(listOfFiles)
                    .filter(File::isFile)
                    .map(File::getName)
                    .filter(e -> FILE_NAME_PATTERN.matcher(e).find())
                    .map(e -> e.split("\\.")[0])
                    .forEach(e -> parseAndSave(dirName, e));
        }
    }

    @Override
    public AgendaSource getAgendaSource() {
        return AgendaSource.VOTES;
    }
}
