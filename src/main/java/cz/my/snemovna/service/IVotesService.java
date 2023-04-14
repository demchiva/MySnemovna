package cz.my.snemovna.service;

import cz.my.snemovna.dto.votes.VoteMembersDto;
import cz.my.snemovna.dto.votes.VoteDetailDto;
import cz.my.snemovna.dto.votes.VoteDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * The service for manage votes agenda.
 */
public interface IVotesService {

    /**
     * The method get votes list. The agenda votes is filtered.
     * @param pageable the page.
     * @return the votes page.
     */
    Page<VoteDto> getVotes(Pageable pageable);

    /**
     * The method get vote detail.
     * @param voteId vote id.
     * @return votes detail.
     */
    VoteDetailDto getVote(Long voteId);

    /**
     * The method get info how members of parliament voted in the given vote.
     * The result is sorted by member id.
     * @param voteId vote id.
     * @return the list of parliament member votes.
     */
    List<VoteMembersDto> getMembers(Long voteId);
}
