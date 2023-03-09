package cz.my.snemovna.jpa.repository.votes;

import cz.my.snemovna.jpa.model.votes.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {
}
