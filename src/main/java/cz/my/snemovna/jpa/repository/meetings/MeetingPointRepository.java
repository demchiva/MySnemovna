package cz.my.snemovna.jpa.repository.meetings;

import cz.my.snemovna.jpa.model.meetings.MeetingPoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetingPointRepository extends JpaRepository<MeetingPoint, Long> {

    List<MeetingPoint> findByMeetingIdAndAgendaType(Long meetingId, Integer type);
}
