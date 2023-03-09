package cz.my.snemovna.jpa.repository.votes;

import cz.my.snemovna.jpa.model.votes.MemberVotes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberVotesRepository extends JpaRepository<MemberVotes, MemberVotes.MemberVotesId> {
}
