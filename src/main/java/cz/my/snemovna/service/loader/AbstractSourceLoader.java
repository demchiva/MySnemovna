package cz.my.snemovna.service.loader;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractSourceLoader implements SourceLoader {

    protected final String dataSourceUrl;

    @Override
    public void load() {
        ArchiveUtils.unZipAndSave(dataSourceUrl, getDirectoryName());
    }

    public abstract String getDirectoryName();

    @Override
    public void delete() {
        ArchiveUtils.deleteDirectory(getDirectoryName());
    }
}
