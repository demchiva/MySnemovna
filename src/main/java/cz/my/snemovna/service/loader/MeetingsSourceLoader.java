package cz.my.snemovna.service.loader;

import cz.my.snemovna.service.parsers.meetings.MeetingsAgendaParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MeetingsSourceLoader extends AbstractSourceLoader {

    private static final String DIR_NAME = "meetings";
    private final MeetingsAgendaParser meetingsAgendaParser;

    public MeetingsSourceLoader(@Value("${snemovna.url.meetings}") String dataSourceUrl,
                                MeetingsAgendaParser meetingsAgendaParser) {
        super(dataSourceUrl);
        this.meetingsAgendaParser = meetingsAgendaParser;
    }

    @Override
    public String getDirectoryName() {
        return DIR_NAME;
    }

    @Override
    public void fill() {
        meetingsAgendaParser.parseAndSave(getDirectoryName());
    }

    @Override
    public int getPriority() {
        return 2;
    }
}
