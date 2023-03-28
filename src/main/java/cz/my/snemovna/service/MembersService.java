package cz.my.snemovna.service;

import cz.my.snemovna.dto.members.MemberDetailDto;
import cz.my.snemovna.dto.members.MemberDto;
import cz.my.snemovna.dto.members.MemberVotesDto;
import cz.my.snemovna.jpa.model.members.Organ;
import cz.my.snemovna.jpa.model.members.ParliamentMember;
import cz.my.snemovna.jpa.model.members.Person;
import cz.my.snemovna.jpa.model.votes.MemberVotes;
import cz.my.snemovna.jpa.model.votes.Vote;
import cz.my.snemovna.jpa.repository.members.OrganRepository;
import cz.my.snemovna.jpa.repository.members.ParliamentMemberRepository;
import cz.my.snemovna.jpa.repository.members.PersonRepository;
import cz.my.snemovna.jpa.repository.votes.MemberVotesRepository;
import cz.my.snemovna.jpa.repository.votes.VoteRepository;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MembersService implements IMembersService {

    private final ParliamentMemberRepository memberRepository;
    private final PersonRepository personRepository;
    private final OrganRepository organRepository;
    private final MemberVotesRepository memberVotesRepository;
    private final VoteRepository voteRepository;
    private final UrlUtils urlUtils;

    // TODO retrieve from db could be optimized. Retrieve only important fields, not all!!
    @Override
    public Page<MemberDto> getMembers(@Nullable final String search, @NotNull final Pageable page) {
        final Page<ParliamentMember> members = getMembersListInternal(search, page);
        final Map<Long, Person> persons = personRepository
                .findAllById(members.stream().map(ParliamentMember::getPersonId).toList())
                .stream()
                .collect(Collectors.toMap(Person::getId, Function.identity()));
        final Map<Long, Organ> organs = getOrgans(members);

        return members.map(e -> createMemberDto(
                e.getId(),
                persons.get(e.getPersonId()),
                organs.get(e.getPartyId()),
                organs.get(e.getRegionId()),
                organs.get(e.getPeriodId())
        ));
    }

    private Page<ParliamentMember> getMembersListInternal(@Nullable String search, @NotNull Pageable page) {
        if (search != null) {
            return memberRepository.findBySearch(search, page);
        }

        return memberRepository.findAll(page);
    }

    private Map<Long, Organ> getOrgans(final Page<ParliamentMember> members) {
        final List<Long> organIds = new ArrayList<>();
        organIds.addAll(members.stream().map(ParliamentMember::getPartyId).toList());
        organIds.addAll(members.stream().map(ParliamentMember::getRegionId).toList());
        organIds.addAll(members.stream().map(ParliamentMember::getPeriodId).toList());

        return organRepository.findAllById(organIds)
                .stream()
                .collect(Collectors.toMap(Organ::getId, Function.identity()));
    }

    private MemberDto createMemberDto(final Long memberId, final Person person, final Organ party,
                                      final Organ region, final Organ period) {
        return new MemberDto(
                memberId,
                getFullNameWithTitles(person),
                party.getShortName(),
                region.getShortName(),
                period.getDateFrom(),
                period.getDateTo()
        );
    }

    @Override
    public String getFullNameWithTitles(final Person person) {
        if (person.getTitleBeforeName() == null && person.getTitleAfterName() == null) {
            return String.format("%s %s", person.getFirstName(), person.getLastName());
        }

        if (person.getTitleBeforeName() != null && person.getTitleAfterName() == null) {
            return String.format("%s %s %s", person.getTitleBeforeName(), person.getFirstName(), person.getLastName());
        }

        if (person.getTitleBeforeName() == null && person.getTitleAfterName() != null) {
            return String.format("%s %s %s", person.getFirstName(), person.getLastName(), person.getTitleAfterName());
        }

        return String.format("%s %s %s %s",
                person.getTitleBeforeName(), person.getFirstName(),
                person.getLastName(), person.getTitleAfterName());
    }

    @Override
    public MemberDetailDto getMember(@NotNull final Long memberId) {
        final ParliamentMember member = memberRepository.findById(memberId).orElseThrow();
        final Person person = personRepository.findById(member.getPersonId()).orElseThrow();
        final Organ party = organRepository.findById(member.getPartyId()).orElseThrow();
        final Organ region = organRepository.findById(member.getRegionId()).orElseThrow();
        final Organ period = organRepository.findById(member.getPeriodId()).orElseThrow();
        return new MemberDetailDto(
                memberId,
                getFullNameWithTitles(person),
                party.getShortName(),
                region.getShortName(),
                period.getDateFrom(),
                period.getDateTo(),
                new MemberDetailDto.OfficeAddress(
                        member.getStreet(),
                        member.getMunicipality(),
                        member.getZip(),
                        member.getPhone()
                ),
                member.getWeb(),
                member.getFacebook(),
                urlUtils.getPspMemberUrl(memberId),
                member.getEmail(),
                person.getBirthdate()
        );
    }

    @Override
    public List<MemberVotesDto> getVotes(@NotNull final Long memberId) {
        final List<MemberVotes> memberVotes = memberVotesRepository.findByMemberId(memberId);
        final Map<Long, Vote> votes = voteRepository
                .findAllById(memberVotes.stream().map(e -> e.getMemberId().getVoteId()).toList())
                .stream()
                .collect(Collectors.toMap(Vote::getId, Function.identity()));
        return memberVotes
                .stream()
                .map(e -> createMemberVotesDto(e, votes.get(e.getMemberId().getVoteId())))
                .toList();
    }

    private MemberVotesDto createMemberVotesDto(MemberVotes memberVotes, Vote vote) {
        return new MemberVotesDto(
                vote.getShortName(),
                LocalDateTime.of(vote.getDate(), vote.getTime()),
                memberVotes.getResult()
        );
    }
}