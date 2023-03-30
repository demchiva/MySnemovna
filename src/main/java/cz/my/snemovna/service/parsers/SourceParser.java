package cz.my.snemovna.service.parsers;

import jakarta.validation.constraints.NotNull;

public interface SourceParser {

    String UNL_COLUMN_DIVIDER = "\\|";

    void parseAndSave(@NotNull String dirName);
    void delete();
    String getSourceName();
    AgendaSource getAgendaSource();
    int getPriority();
}
