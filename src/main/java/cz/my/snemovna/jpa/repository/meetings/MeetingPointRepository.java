package cz.my.snemovna.jpa.repository.meetings;

import cz.my.snemovna.jpa.model.meetings.MeetingAgendaId;
import cz.my.snemovna.jpa.model.meetings.MeetingPoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingPointRepository extends JpaRepository<MeetingPoint, MeetingAgendaId> {
}
