package cz.my.snemovna.jpa.repository.meetings;

import cz.my.snemovna.jpa.model.meetings.MeetingState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingStateRepository extends JpaRepository<MeetingState, Long> {
}
