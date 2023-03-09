package cz.my.snemovna.service.parsers.meetings;

import cz.my.snemovna.jpa.model.meetings.MeetingAgendaId;
import cz.my.snemovna.jpa.model.meetings.MeetingPoint;
import cz.my.snemovna.jpa.repository.meetings.MeetingPointRepository;
import cz.my.snemovna.service.parsers.AbstractSourceParser;
import cz.my.snemovna.service.parsers.AgendaSource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MeetingPointParser extends AbstractSourceParser<MeetingPoint, MeetingAgendaId> {

    public MeetingPointParser(MeetingPointRepository repository) {
        super(repository);
    }

    @Override
    protected MeetingPoint convert(List<String> sourceData) {
        final MeetingPoint meetingPoint = new MeetingPoint();
        Integer agendaType = safeParseToInteger(sourceData.get(9));
        agendaType = agendaType == null ? 0 : agendaType;
        meetingPoint.setId(new MeetingAgendaId(Long.parseLong(sourceData.get(0)), agendaType));
        meetingPoint.setMeetingId(safeParseToLong(sourceData.get(1)));
        meetingPoint.setPrintId(safeParseToLong(sourceData.get(2)));
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
