package cz.my.snemovna.jpa.repository.meetings;

import cz.my.snemovna.jpa.model.meetings.Meeting;
import cz.my.snemovna.jpa.model.meetings.MeetingAgendaId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * The meeting repository.
 */
public interface MeetingRepository extends JpaRepository<Meeting, MeetingAgendaId> {

    List<Meeting> findByMeetingNumberAndOrganId(Long meetingNumber, Long organId);
}
