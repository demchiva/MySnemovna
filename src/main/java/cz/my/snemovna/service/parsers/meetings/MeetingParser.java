package cz.my.snemovna.service.parsers.meetings;

import cz.my.snemovna.jpa.model.meetings.Meeting;
import cz.my.snemovna.jpa.model.meetings.MeetingAgendaId;
import cz.my.snemovna.service.parsers.AbstractSourceParser;
import cz.my.snemovna.service.parsers.AgendaSource;
import jakarta.persistence.EntityManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The source parser for meetings.
 */
@Component
public class MeetingParser extends AbstractSourceParser<Meeting> {

    public MeetingParser(final JdbcTemplate jdbcTemplate, final EntityManager entityManager) {
        super(Meeting.class, jdbcTemplate, entityManager);
    }

    @Override
    protected String getColumnsOrder() {
        return "id,agenda_type,organ_id,meeting_number,date_from,date_to,updated_at";
    }

    @Override
    protected Meeting convert(List<String> sourceData) {
        Integer agendaType = safeParseToInteger(sourceData.get(6));
        agendaType = agendaType == null ? 0 : agendaType;
        final Long id = Long.parseLong(sourceData.get(0));

        Meeting meeting = new Meeting();
        meeting.setId(new MeetingAgendaId(id, agendaType));
        meeting.setOrganId(safeParseToLong(sourceData.get(1)));
        meeting.setMeetingNumber(safeParseToLong(sourceData.get(2)));
        meeting.setDateFrom(sourceData.get(3));
        meeting.setDateTo(sourceData.get(4));
        meeting.setUpdatedAt(sourceData.get(5));

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
