package cz.my.snemovna.jpa.repository.votes;

import cz.my.snemovna.jpa.model.votes.VoteQuestioning;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteQuestioningRepository extends JpaRepository<VoteQuestioning, Long> {
}
