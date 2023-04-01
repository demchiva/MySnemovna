package cz.my.snemovna.service.parsers.meetings;

import cz.my.snemovna.jpa.model.meetings.MeetingState;
import cz.my.snemovna.service.parsers.AbstractSourceParser;
import cz.my.snemovna.service.parsers.AgendaSource;
import jakarta.persistence.EntityManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The source parser for meeting states.
 */
@Component
public class MeetingStateParser extends AbstractSourceParser<MeetingState> {

    public MeetingStateParser(final JdbcTemplate jdbcTemplate, final EntityManager entityManager) {
        super(MeetingState.class, jdbcTemplate, entityManager);
    }

    @Override
    protected String getColumnsOrder() {
        return "meeting_id,state,type,meeting_begin_text,meeting_status_text,meeting_status_text_2";
    }

    @Override
    protected Object[] convert(List<String> sourceData) {
        return new Object[] {
                Long.parseLong(sourceData.get(0)),
                safeParseToInteger(sourceData.get(1)),
                safeParseToInteger(sourceData.get(2)),
                sourceData.get(3),
                sourceData.get(4),
                sourceData.get(5)
        };
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
