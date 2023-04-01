package cz.my.snemovna.service.parsers.meetings;

import cz.my.snemovna.jpa.model.meetings.MeetingPoint;
import cz.my.snemovna.service.parsers.AbstractSourceParser;
import cz.my.snemovna.service.parsers.AgendaSource;
import jakarta.persistence.EntityManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The source parser for meeting points.
 */
@Component
public class MeetingPointParser extends AbstractSourceParser<MeetingPoint> {

    public MeetingPointParser(final JdbcTemplate jdbcTemplate, final EntityManager entityManager) {
        super(MeetingPoint.class, jdbcTemplate, entityManager);
    }

    @Override
    protected String getColumnsOrder() {
        return "point_id,agenda_type,meeting_id,print_id,type_id,point_number,full_name,name_suffix," +
                "note,state_id,mode,short_note,type,document_id,short_name";
    }

    @Override
    protected Object[] convert(List<String> sourceData) {
        Integer agendaType = safeParseToInteger(sourceData.get(9));
        agendaType = agendaType == null ? 0 : agendaType;
        return new Object[] {
                Long.parseLong(sourceData.get(0)),
                agendaType,
                safeParseToLong(sourceData.get(1)),
                safeParseToLong(sourceData.get(2)),
                safeParseToInteger(sourceData.get(3)),
                safeParseToLong(sourceData.get(4)),
                sourceData.get(5),
                sourceData.get(6),
                sourceData.get(7),
                safeParseToLong(sourceData.get(8)),
                safeParseToLong(sourceData.get(10)),
                sourceData.get(11),
                safeParseToInteger(sourceData.get(12)),
                safeParseToLong(sourceData.get(13)),
                sourceData.get(14)
        };
    }

    @Override
    public String getSourceName() {
        return "bod_schuze";
    }

    @Override
    public AgendaSource getAgendaSource() {
        return AgendaSource.MEETINGS;
    }
}
