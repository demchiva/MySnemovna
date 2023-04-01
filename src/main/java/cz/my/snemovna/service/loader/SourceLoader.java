package cz.my.snemovna.service.loader;

/**
 * The interface for snemovna agenda source loaders.
 */
public interface SourceLoader {

    /**
     * The method load agenda data from data source.
     * On the of the method directories with files (.unl) are expected.
     */
    void load();

    /**
     * The method fill database with data from loaded files.
     */
    void fill();

    /**
     * The method clean files and directories after end db filling.
     */
    void delete();

    /**
     * The method get the priority of agenda filling to db.
     */
    int getPriority();
}
