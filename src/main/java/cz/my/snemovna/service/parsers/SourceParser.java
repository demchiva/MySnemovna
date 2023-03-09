package cz.my.snemovna.service.parsers;

import jakarta.validation.constraints.NotNull;

public interface SourceParser {

    String UNL_COLUMN_DIVIDER = "\\|";

    void parseAndSave(@NotNull String dirName);
    String getSourceName();
    AgendaSource getAgendaSource();
    int getPriority();
}
