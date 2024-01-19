package cz.my.snemovna.service.parsers.meetings;

import cz.my.snemovna.jpa.model.meetings.MeetingPointState;
import cz.my.snemovna.service.parsers.AbstractSourceParser;
import cz.my.snemovna.service.parsers.AgendaSource;
import jakarta.persistence.EntityManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The source parser for meeting point states.
 */
@Component
public class MeetingPointStateParser extends AbstractSourceParser<MeetingPointState> {

    public MeetingPointStateParser(final JdbcTemplate jdbcTemplate, final EntityManager entityManager) {
        super(MeetingPointState.class, jdbcTemplate, entityManager);
    }

    @Override
    protected String getColumnsOrder() {
        return "id,description";
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
