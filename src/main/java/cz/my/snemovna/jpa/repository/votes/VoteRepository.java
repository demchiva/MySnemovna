package cz.my.snemovna.jpa.repository.votes;

import cz.my.snemovna.jpa.model.votes.Vote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * The vote repository.
 */
public interface VoteRepository extends JpaRepository<Vote, Long> {

    @Query("select v from Vote v where v.pointNumber > 0")
    Page<Vote> findAllExceptAgendas(Pageable page);

    @Query("select v from Vote v where v.pointNumber > 0 and v.id in :ids order by v.id desc")
    List<Vote> findAllExceptAgendas(Iterable<Long> ids);
}
