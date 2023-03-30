package cz.my.snemovna.rest;

import cz.my.snemovna.dto.members.MemberDetailDto;
import cz.my.snemovna.dto.members.MemberDto;
import cz.my.snemovna.dto.members.MemberVotesDto;
import cz.my.snemovna.rest.api.IRestMembers;
import cz.my.snemovna.service.IMembersService;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/v1/members")
@RestController
@RequiredArgsConstructor
public class RestMembers implements IRestMembers {

    private final IMembersService membersService;

    @GetMapping("")
    @Override
    public Page<MemberDto> getMembers(@RequestParam("search") @Nullable final String search,
                                      @RequestBody final PageApiRequest page) {
        return membersService.getMembers(search, PageRequest.of(page.getPage(), page.getSize(),
                page.getDirection(), page.getProperty()));
    }

    @GetMapping("/{memberId}")
    @Override
    public MemberDetailDto getMember(@PathVariable("memberId") @NotNull final Long memberId) {
        return membersService.getMember(memberId);
    }

    @GetMapping("/{memberId}/votes")
    @Override
    public List<MemberVotesDto> getVotes(@PathVariable("memberId") @NotNull final Long memberId) {
        return membersService.getVotes(memberId);
    }
}
