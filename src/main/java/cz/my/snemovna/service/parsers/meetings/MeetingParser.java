package cz.my.snemovna.service.parsers.meetings;

import cz.my.snemovna.jpa.model.meetings.Meeting;
import cz.my.snemovna.jpa.model.meetings.MeetingAgendaId;
import cz.my.snemovna.jpa.repository.meetings.MeetingRepository;
import cz.my.snemovna.service.parsers.AbstractSourceParser;
import cz.my.snemovna.service.parsers.AgendaSource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MeetingParser extends AbstractSourceParser<Meeting, MeetingAgendaId> {

    public MeetingParser(MeetingRepository repository) {
        super(repository);
    }

    @Override
    protected Meeting convert(List<String> sourceData) {
        final Meeting meeting = new Meeting();
        Integer agendaType = safeParseToInteger(sourceData.get(6));
        agendaType = agendaType == null ? 0 : agendaType;
        meeting.setId(new MeetingAgendaId(Long.parseLong(sourceData.get(0)), agendaType));
        meeting.setOrganId(safeParseToLong(sourceData.get(1)));
        meeting.setMeetingNumber(safeParseToLong(sourceData.get(2)));
        meeting.setDateFrom(safeParseDateWithHours(sourceData.get(3)));
        meeting.setDateTo(safeParseDateWithHours(sourceData.get(4)));
        meeting.setUpdatedAt(safeParseDateWithHours(sourceData.get(5)));
        return meeting;
    }

    @Override
    public String getSourceName() {
        return "schuze";
    }

    @Override
    public AgendaSource getAgendaSource() {
        return AgendaSource.MEETINGS;
    }
}
