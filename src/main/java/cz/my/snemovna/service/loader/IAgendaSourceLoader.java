package cz.my.snemovna.service.loader;

/**
 * The service for encapsulate agenda source loading.
 */
public interface IAgendaSourceLoader {

    /**
     * The method loads all agenda data, parse and fill it to database.
     */
    void load();
}
