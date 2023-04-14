package cz.my.snemovna.jpa.repository.votes;

import cz.my.snemovna.jpa.model.votes.MemberVotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * The member votes results repository.
 */
public interface MemberVotesRepository extends JpaRepository<MemberVotes, MemberVotes.MemberVotesId> {

    @Query("select mv from MemberVotes mv where mv.id.voteId = :voteId")
    List<MemberVotes> findByVotesId(@Param("voteId") Long voteId);

    @Query("select mv from MemberVotes mv where mv.id.memberId = :memberId order by mv.id.voteId desc")
    List<MemberVotes> findByMemberId(@Param("memberId") Long memberId);
}
