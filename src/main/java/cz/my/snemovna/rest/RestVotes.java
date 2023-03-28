package cz.my.snemovna.rest;

import cz.my.snemovna.dto.votes.VoteMembersDto;
import cz.my.snemovna.dto.votes.VoteDetailDto;
import cz.my.snemovna.dto.votes.VoteDto;
import cz.my.snemovna.rest.api.IRestVotes;
import cz.my.snemovna.service.IVotesService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/v1/votes")
@RestController
@RequiredArgsConstructor
public class RestVotes implements IRestVotes {

    private final IVotesService votesService;

    @GetMapping("")
    @Override
    public Page<VoteDto> getVotes(@RequestBody Pageable page) {
        return votesService.getVotes(page);
    }

    @GetMapping("/{voteId}")
    @Override
    public VoteDetailDto getVote(@PathVariable("voteId") @NotNull Long voteId) {
        return votesService.getVote(voteId);
    }

    @GetMapping("/{voteId}/members")
    @Override
    public List<VoteMembersDto> getMembers(@PathVariable("voteId") @NotNull Long voteId) {
        return votesService.getMembers(voteId);
    }
}
