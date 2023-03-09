package cz.my.snemovna.service.parsers.votes;

import cz.my.snemovna.service.parsers.AbstractSourceParser;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class AbstractVoteWithYearSourceParser<T, E> extends AbstractSourceParser<T, E> {

    public AbstractVoteWithYearSourceParser(JpaRepository<T, E> repository) {
        super(repository);
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
