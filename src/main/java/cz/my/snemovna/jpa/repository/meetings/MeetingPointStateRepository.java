package cz.my.snemovna.jpa.repository.meetings;

import cz.my.snemovna.jpa.model.meetings.MeetingPointState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingPointStateRepository extends JpaRepository<MeetingPointState, Long> {
}
