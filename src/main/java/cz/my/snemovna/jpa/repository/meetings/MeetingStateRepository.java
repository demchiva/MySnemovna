package cz.my.snemovna.jpa.repository.meetings;

import cz.my.snemovna.jpa.model.meetings.MeetingState;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The meeting state repository.
 */
public interface MeetingStateRepository extends JpaRepository<MeetingState, Long> {
}
