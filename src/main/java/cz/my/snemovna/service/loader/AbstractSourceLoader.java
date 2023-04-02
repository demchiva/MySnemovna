package cz.my.snemovna.service.loader;

import lombok.RequiredArgsConstructor;

/**
 * The abstract and default implementation of {@link SourceLoader}.
 */
@RequiredArgsConstructor
public abstract class AbstractSourceLoader implements SourceLoader {

    protected final String dataSourceUrl;
    protected final ArchiveUtils archiveUtils;

    @Override
    public void load() {
        archiveUtils.unZipAndSave(dataSourceUrl, getDirectoryName());
    }

    /**
     * The method get the directory name to save files.
     */
    public abstract String getDirectoryName();

    @Override
    public void delete() {
        archiveUtils.deleteDirectory(getDirectoryName());
    }
}
