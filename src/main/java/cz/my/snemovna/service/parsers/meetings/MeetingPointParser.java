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
    protected MeetingPoint convert(List<String> sourceData) {
        final MeetingPoint meetingPoint = new MeetingPoint();

        Integer agendaType = safeParseToInteger(sourceData.get(9));
        agendaType = agendaType == null ? 0 : agendaType;

        meetingPoint.setId(Long.parseLong(sourceData.get(0)));
        meetingPoint.setAgendaType(agendaType);
        meetingPoint.setPointId(safeParseToLong(sourceData.get(1)));
        meetingPoint.setMeetingId(safeParseToLong(sourceData.get(2)));
        meetingPoint.setTypeId(safeParseToInteger(sourceData.get(3)));
        meetingPoint.setPointNumber(safeParseToLong(sourceData.get(4)));
        meetingPoint.setFullName(sourceData.get(5));
        meetingPoint.setNameSuffix(sourceData.get(6));
        meetingPoint.setNote(sourceData.get(7));
        meetingPoint.setStateId(safeParseToLong(sourceData.get(8)));
        meetingPoint.setMode(safeParseToLong(sourceData.get(10)));
        meetingPoint.setShortNote(sourceData.get(11));
        meetingPoint.setType(safeParseToInteger(sourceData.get(12)));
        meetingPoint.setDocumentId(safeParseToLong(sourceData.get(13)));
        meetingPoint.setShortName(sourceData.get(14));

        return meetingPoint;
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
