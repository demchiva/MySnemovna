package cz.my.snemovna.rest;

import cz.my.snemovna.dto.votes.VoteDetailDto;
import cz.my.snemovna.dto.votes.VoteDto;
import cz.my.snemovna.dto.votes.VoteMembersDto;
import cz.my.snemovna.rest.api.IRestVotes;
import cz.my.snemovna.service.IVotesService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The api for manage votes agenda.
 */
@RequestMapping("/api/v1/votes")
@RestController
@RequiredArgsConstructor
public class RestVotes implements IRestVotes {

    private final IVotesService votesService;

    /**
     * Get the votes for votes listing.
     * @param page the page
     * @return the votes page.
     */
    @GetMapping("")
    @Override
    public Page<VoteDto> getVotes(@Valid final PageApiRequest page) {
        return votesService.getVotes(PageRequest.of(page.getPage(), page.getSize(),
                page.getDirection(), page.getProperty()));
    }

    /**
     * Get vote detail.
     * @param voteId vote id.
     * @return the vote detail.
     */
    @GetMapping("/{voteId}")
    @Override
    public VoteDetailDto getVote(@PathVariable("voteId") @NotNull final Long voteId) {
        return votesService.getVote(voteId);
    }

    /**
     * Get info how members of parliament voted in the given vote.
     * @param voteId vote id.
     * @return the list of parliament member votes.
     */
    @GetMapping("/{voteId}/members")
    @Override
    public List<VoteMembersDto> getMembers(@PathVariable("voteId") @NotNull final Long voteId) {
        return votesService.getMembers(voteId);
    }
}
