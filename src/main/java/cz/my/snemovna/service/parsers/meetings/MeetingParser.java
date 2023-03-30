package cz.my.snemovna.service.parsers.meetings;

import cz.my.snemovna.jpa.model.meetings.Meeting;
import cz.my.snemovna.service.parsers.AbstractSourceParser;
import cz.my.snemovna.service.parsers.AgendaSource;
import jakarta.persistence.EntityManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

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
    protected Object[] convert(List<String> sourceData) {
        Integer agendaType = safeParseToInteger(sourceData.get(6));
        agendaType = agendaType == null ? 0 : agendaType;
        return new Object[] {
                Long.parseLong(sourceData.get(0)),
                agendaType,
                safeParseToLong(sourceData.get(1)),
                safeParseToLong(sourceData.get(2)),
                sourceData.get(3),
                sourceData.get(4),
                sourceData.get(5)
        };
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
