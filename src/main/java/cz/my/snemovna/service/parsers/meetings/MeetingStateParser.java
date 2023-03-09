package cz.my.snemovna.service.parsers.meetings;

import cz.my.snemovna.jpa.model.meetings.MeetingState;
import cz.my.snemovna.jpa.repository.meetings.MeetingStateRepository;
import cz.my.snemovna.service.parsers.AbstractSourceParser;
import cz.my.snemovna.service.parsers.AgendaSource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MeetingStateParser extends AbstractSourceParser<MeetingState, Long> {

    public MeetingStateParser(MeetingStateRepository repository) {
        super(repository);
    }

    @Override
    protected MeetingState convert(List<String> sourceData) {
        final MeetingState meetingState = new MeetingState();
        meetingState.setMeetingId(Long.parseLong(sourceData.get(0)));
        meetingState.setState(safeParseToInteger(sourceData.get(1)));
        meetingState.setType(safeParseToInteger(sourceData.get(2)));
        meetingState.setMeetingBeginText(sourceData.get(3));
        meetingState.setMeetingStatusText(sourceData.get(4));
        meetingState.setMeetingStatusText2(sourceData.get(5));
        return meetingState;
    }

    @Override
    public String getSourceName() {
        return "schuze_stav";
    }

    @Override
    public AgendaSource getAgendaSource() {
        return AgendaSource.MEETINGS;
    }
}
