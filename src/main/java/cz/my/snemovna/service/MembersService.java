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
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MembersService implements IMembersService {

    private static final String EMPTY_VALUE = "\\ ";

    private final ParliamentMemberRepository memberRepository;
    private final PersonRepository personRepository;
    private final OrganRepository organRepository;
    private final MemberVotesRepository memberVotesRepository;
    private final VoteRepository voteRepository;
    private final UrlUtils urlUtils;

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
                getPhotoUrl(period.getDateFrom().getYear(), person.getId()),
                getFullNameWithTitles(person).trim(),
                party != null ? party.getNameCz() : null,
                region != null ? region.getNameCz() : null,
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
        final Organ party = member.getPartyId() != null
                ? organRepository.findById(member.getPartyId()).orElse(null)
                : null;
        final Organ region = organRepository.findById(member.getRegionId()).orElse(null);
        final Organ period = organRepository.findById(member.getPeriodId()).orElseThrow();
        return new MemberDetailDto(
                memberId,
                getPhotoUrl(period.getDateFrom().getYear(), person.getId()),
                getFullNameWithTitles(person).trim(),
                party != null ? party.getNameCz() : null,
                region != null ? region.getNameCz() : null,
                period.getDateFrom(),
                period.getDateTo(),
                isValidOffice(member)
                    ? new MemberDetailDto.OfficeAddress(
                        member.getStreet(),
                        member.getMunicipality(),
                        member.getZip(),
                        member.getPhone()
                    ) : null,
                isAcceptableValue(member.getWeb()) ? member.getWeb() : null,
                isAcceptableValue(member.getFacebook()) ? member.getFacebook() : null,
                urlUtils.getPspMemberUrl(person.getId()),
                isAcceptableValue(member.getEmail()) ? member.getEmail() : null,
                person.getBirthdate()
        );
    }

    private boolean isValidOffice(final ParliamentMember member) {
        return isAcceptableValue(member.getStreet())
                || isAcceptableValue(member.getMunicipality())
                || isAcceptableValue(member.getZip())
                || isAcceptableValue(member.getPhone());
    }

    private boolean isAcceptableValue(final String value) {
        return value != null && !value.isEmpty() && !EMPTY_VALUE.equals(value);
    }

    @Override
    public List<MemberVotesDto> getVotes(@NotNull final Long memberId) {
        final Optional<ParliamentMember> member = memberRepository.findById(memberId);
        if (member.isEmpty()) {
            throw new NoSuchElementException("Member not found.");
        }

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

    private String getPhotoUrl(final int year, final Long personId) {
        return urlUtils.getMemberPhotoUrl(year, personId);
    }
}
