package cz.my.snemovna.service;

import cz.my.snemovna.dto.votes.VoteDetailDto;
import cz.my.snemovna.dto.votes.VoteDto;
import cz.my.snemovna.dto.votes.VoteMembersDto;
import cz.my.snemovna.jpa.model.meetings.Meeting;
import cz.my.snemovna.jpa.model.meetings.MeetingPoint;
import cz.my.snemovna.jpa.model.meetings.MeetingPointState;
import cz.my.snemovna.jpa.model.members.Organ;
import cz.my.snemovna.jpa.model.members.ParliamentMember;
import cz.my.snemovna.jpa.model.members.Person;
import cz.my.snemovna.jpa.model.votes.MemberVotes;
import cz.my.snemovna.jpa.model.votes.Vote;
import cz.my.snemovna.jpa.repository.meetings.MeetingPointRepository;
import cz.my.snemovna.jpa.repository.meetings.MeetingPointStateRepository;
import cz.my.snemovna.jpa.repository.meetings.MeetingRepository;
import cz.my.snemovna.jpa.repository.members.OrganRepository;
import cz.my.snemovna.jpa.repository.members.ParliamentMemberRepository;
import cz.my.snemovna.jpa.repository.members.PersonRepository;
import cz.my.snemovna.jpa.repository.votes.MemberVotesRepository;
import cz.my.snemovna.jpa.repository.votes.VoteRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static cz.my.snemovna.dto.meetings.MeetingAgendaType.APPROVED;

@Service
@RequiredArgsConstructor
public class VotesService implements IVotesService {

    private final MemberVotesRepository memberVotesRepository;
    private final VoteRepository voteRepository;
    private final ParliamentMemberRepository parliamentMemberRepository;
    private final PersonRepository personRepository;
    private final OrganRepository organRepository;
    private final UrlUtils urlUtils;
    private final IMembersService membersService;
    private final MeetingPointStateRepository meetingPointStateRepository;
    private final MeetingPointRepository meetingPointRepository;
    private final IMeetingsService meetingsService;
    private final MeetingRepository meetingRepository;

    @Override
    public Page<VoteDto> getVotes(@NotNull final Pageable pageable) {
        return voteRepository.findAll(pageable).map(this::convertVoteToDto);
    }

    private VoteDto convertVoteToDto(final Vote vote) {
        return new VoteDto(
                vote.getId(),
                vote.getResult(),
                vote.getLongName(),
                vote.getAye(),
                vote.getNo(),
                vote.getAbstained(),
                LocalDateTime.of(vote.getDate(), vote.getTime())
        );
    }

    @Override
    public VoteDetailDto getVote(@NotNull final Long voteId) {
        final Vote vote = voteRepository.findById(voteId).orElseThrow();

        // Find the meeting of the organ (PS). Typically, return proposal and approved with same ids.
        final List<Meeting> meetings = meetingRepository.findByMeetingNumberAndOrganId(vote.getMeetingNumber(), vote.getOrganId());
        if (meetings == null || meetings.isEmpty()) {
            throw new NoSuchElementException("Meeting not found for vote.");
        }

        final Meeting meeting = meetings.get(0);
        final List<MeetingPoint> points = meetingPointRepository
                .findByPointNumberAndAgendaTypeAndMeetingId(vote.getPointNumber(), APPROVED.getType(), meeting.getId().getId());
        if (points == null || points.isEmpty()) {
            throw new NoSuchElementException("Meeting point not found for vote.");
        }

        final MeetingPointState pointState = points.get(0).getStateId() == null
                ? null
                : meetingPointStateRepository.findById(points.get(0).getStateId()).orElse(null);
        return convertVoteToDetailDto(vote, points.get(0), pointState, meeting);
    }

    private VoteDetailDto convertVoteToDetailDto(final Vote vote, final MeetingPoint point,
                                                 final MeetingPointState pointState, final Meeting meeting) {
        return new VoteDetailDto(
                vote.getId(),
                vote.getResult(),
                vote.getAye(),
                vote.getNo(),
                vote.getAbstained(),
                LocalDateTime.of(vote.getDate(), vote.getTime()),
                vote.getVoteNumber(),
                vote.getPointNumber(),
                vote.getQuorum(),
                urlUtils.getVoteUrl(vote.getId()),
                vote.getLongName(),
                point.getFullName(),
                pointState != null ? pointState.getDescription() : null,
                meetingsService.getType(point),
                meeting.getMeetingNumber()
        );
    }

    @Override
    public List<VoteMembersDto> getMembers(@NotNull final Long voteId) {
       return new MemberDtoCreator(voteId).createMemberDtos();
    }

    private class MemberDtoCreator {

        private final Map<Long, MemberVotes> membersVotes;
        private final Map<Long, ParliamentMember> members;
        private final Map<Long, Person> persons;
        private final Map<Long, Organ> parties;
        private final Organ period;

        public MemberDtoCreator(final Long voteId) {
            final Optional<Vote> vote = voteRepository.findById(voteId);

            if (vote.isEmpty()) {
                throw new NoSuchElementException("Vote not found");
            }

            this.membersVotes = memberVotesRepository.findByVotesId(voteId)
                    .stream()
                    .collect(Collectors.toMap(e -> e.getMemberId().getMemberId(), Function.identity()));
            this.members = parliamentMemberRepository.findAllById(membersVotes.keySet())
                    .stream()
                    .collect(Collectors.toMap(ParliamentMember::getId, Function.identity()));
            this.persons = personRepository
                    .findAllById(members.values().stream().map(ParliamentMember::getPersonId).toList())
                    .stream()
                    .collect(Collectors.toMap(Person::getId, Function.identity()));
            this.parties = organRepository.findAllById(members.values().stream().map(ParliamentMember::getPartyId).toList())
                    .stream()
                    .collect(Collectors.toMap(Organ::getId, Function.identity()));
            this.period = membersVotes.keySet().isEmpty()
                    ? null
                    : organRepository.findById(
                            members.values().stream().findAny().map(ParliamentMember::getPeriodId).orElseThrow()
                    ).orElse(null);
        }

        private List<VoteMembersDto> createMemberDtos() {
            return membersVotes
                    .values()
                    .stream()
                    .map(this::createMemberDto)
                    .sorted(Comparator.comparing(VoteMembersDto::name))
                    .toList();
        }

        private VoteMembersDto createMemberDto(@NotNull final MemberVotes memberVotes) {
            final ParliamentMember member = members.get(memberVotes.getMemberId().getMemberId());
            final Person person = persons.get(member.getPersonId());
            final Organ party = parties.get(member.getPartyId());
            return new VoteMembersDto(
                    membersService.getFullNameWithTitles(person).trim(),
                    party.getShortName(),
                    member.isPhoto() ? getPhotoUrl(period.getDateFrom().getYear(), person.getId()) : null,
                    memberVotes.getResult()
            );
        }

        private String getPhotoUrl(final int year, final Long personId) {
            return urlUtils.getMemberPhotoUrl(year, personId);
        }
    }
}
