package cz.my.snemovna.service.parsers.meetings;

import cz.my.snemovna.jpa.model.meetings.MeetingPointState;
import cz.my.snemovna.jpa.repository.meetings.MeetingPointStateRepository;
import cz.my.snemovna.service.parsers.AbstractSourceParser;
import cz.my.snemovna.service.parsers.AgendaSource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MeetingPointStateParser extends AbstractSourceParser<MeetingPointState, Long> {

    public MeetingPointStateParser(MeetingPointStateRepository repository) {
        super(repository);
    }

    @Override
    protected MeetingPointState convert(List<String> sourceData) {
        final MeetingPointState meetingPointState = new MeetingPointState();
        meetingPointState.setId(Long.parseLong(sourceData.get(0)));
        meetingPointState.setDescription(sourceData.get(1));
        return meetingPointState;
    }

    @Override
    public String getSourceName() {
        return "bod_stav";
    }

    @Override
    public AgendaSource getAgendaSource() {
        return AgendaSource.MEETINGS;
    }
}
