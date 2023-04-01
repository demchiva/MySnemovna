package cz.my.snemovna.service.parsers;

import jakarta.validation.constraints.NotNull;

/**
 * The interface for snemovna agenda source parsers.
 */
public interface SourceParser {

    /**
     * The unl file format column divider.
     */
    String UNL_COLUMN_DIVIDER = "\\|";

    /**
     * The method parses given directory name and file name {@link this#getSourceName()}, and save data to db.
     */
    void parseAndSave(@NotNull String dirName);

    /**
     * The method cleans source db data.
     */
    void delete();

    /**
     * The method get source file name.
     */
    String getSourceName();

    /**
     * The method get info to which agenda the parser is related.
     */
    AgendaSource getAgendaSource();

    /**
     * The method get the priority of parsing.
     * Used in case of some data dependencies or constrains to prevent foreign keys errors.
     */
    int getPriority();
}
