package cz.my.snemovna.jpa.repository.meetings;

import cz.my.snemovna.jpa.model.meetings.MeetingPoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * The meeting point repository.
 */
public interface MeetingPointRepository extends JpaRepository<MeetingPoint, Long> {

    List<MeetingPoint> findByMeetingIdAndAgendaType(Long meetingId, Integer type);

    List<MeetingPoint> findByPointNumberAndAgendaTypeAndMeetingId(Long pointNumber, Integer agendaType, Long meetingId);
}
