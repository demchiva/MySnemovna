package cz.my.snemovna.jpa.repository.votes;

import cz.my.snemovna.jpa.model.votes.MemberVotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberVotesRepository extends JpaRepository<MemberVotes, MemberVotes.MemberVotesId> {

    @Query("select mv from MemberVotes mv where mv.memberId.voteId = :voteId")
    List<MemberVotes> findByVotesId(@Param("voteId") Long voteId);
}
