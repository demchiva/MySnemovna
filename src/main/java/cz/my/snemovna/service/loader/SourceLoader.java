package cz.my.snemovna.service.loader;

/**
 * The interface for snemovna agenda source loaders.
 */
public interface SourceLoader {
    void load();
    void fill();
    void delete();
    int getPriority();
}
