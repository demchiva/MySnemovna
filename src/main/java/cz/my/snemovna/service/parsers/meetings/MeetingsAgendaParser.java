package cz.my.snemovna.service.parsers.meetings;

import cz.my.snemovna.service.parsers.AbstractAgendaParser;
import cz.my.snemovna.service.parsers.AgendaSource;
import cz.my.snemovna.service.parsers.SourceParser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeetingsAgendaParser extends AbstractAgendaParser {

    public MeetingsAgendaParser(List<SourceParser> sourceParsers) {
        super(sourceParsers);
    }

    @Override
    protected AgendaSource getAgendaSource() {
        return AgendaSource.MEETINGS;
    }
}
