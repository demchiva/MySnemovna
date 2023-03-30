package cz.my.snemovna.service.parsers.votes;

import cz.my.snemovna.service.parsers.AbstractSourceParser;
import jakarta.persistence.EntityManager;
import org.springframework.jdbc.core.JdbcTemplate;

public abstract class AbstractVoteWithYearSourceParser<T> extends AbstractSourceParser<T> {

    protected AbstractVoteWithYearSourceParser(final Class<T> type,
                                               final JdbcTemplate jdbcTemplate,
                                               final EntityManager entityManager) {
        super(type, jdbcTemplate, entityManager);
    }

    /**
     * The method fix the source path for specific file names.
     * @param dirName directory name in format: votes_year (e.g. votes_2021)
     * @param sourceName the result of {@link this#getSourceName()}
     * @return the fixed source file path.
     */
    @Override
    protected String createSourcePath(final String dirName, final String sourceName) {
        final String year = dirName.split("_")[1];
        final String fixedSourceName = sourceName.replace("XXXX", year);
        return super.createSourcePath(dirName, fixedSourceName);
    }
}
