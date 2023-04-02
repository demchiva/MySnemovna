package cz.my.snemovna.service;

import com.fasterxml.jackson.core.type.TypeReference;
import cz.my.snemovna.AbstractSnemovnaTest;
import cz.my.snemovna.TestUtils;
import cz.my.snemovna.jpa.model.meetings.Meeting;
import cz.my.snemovna.jpa.model.meetings.MeetingAgendaId;
import cz.my.snemovna.jpa.model.meetings.MeetingPoint;
import cz.my.snemovna.jpa.model.members.Organ;
import cz.my.snemovna.jpa.model.members.ParliamentMember;
import cz.my.snemovna.jpa.model.members.Person;
import cz.my.snemovna.jpa.model.votes.MemberVotes;
import cz.my.snemovna.jpa.repository.meetings.MeetingPointRepository;
import cz.my.snemovna.jpa.repository.meetings.MeetingRepository;
import cz.my.snemovna.jpa.repository.members.OrganRepository;
import cz.my.snemovna.jpa.repository.members.ParliamentMemberRepository;
import cz.my.snemovna.jpa.repository.members.PersonRepository;
import cz.my.snemovna.jpa.repository.votes.MemberVotesRepository;
import cz.my.snemovna.service.loader.AgendaSourceLoader;
import cz.my.snemovna.service.loader.ArchiveUtils;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class AgendaSourceLoaderTest extends AbstractSnemovnaTest {

    private static final Long MEMBER_ID = 1743L;
    private static final Long MEETING_ID = 718L;

    @Autowired
    private AgendaSourceLoader agendaSourceLoader;

    @Autowired
    @MockBean
    private ArchiveUtils archiveUtils;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ParliamentMemberRepository memberRepository;

    @Autowired
    private MemberVotesRepository memberVotesRepository;

    @Autowired
    private OrganRepository organRepository;

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private MeetingPointRepository meetingPointRepository;

    @Test
    @SneakyThrows
    void testSuccessLoad() {
        doNothing().when(archiveUtils).unZipAndSave(anyString(), anyString());
        doNothing().when(archiveUtils).deleteDirectory(anyString());
        when(archiveUtils.isDirectoryExist("votes_2021")).thenReturn(true);

        agendaSourceLoader.load();

        Optional<ParliamentMember> memberOptional = memberRepository.findById(MEMBER_ID);
        assertTrue(memberOptional.isPresent());

        ParliamentMember actualMember = memberOptional.get();
        ParliamentMember expectedMember = objectMapper.readValue(
                TestUtils.getResourceContent("testdata/loaders/expectedMember.json"), new TypeReference<>() {});
        assertEquals(expectedMember, actualMember);

        Optional<Person> personOptional = personRepository.findById(actualMember.getPersonId());
        assertTrue(personOptional.isPresent());

        Person actualPerson = personOptional.get();
        Person expectedPerson = objectMapper.readValue(
                TestUtils.getResourceContent("testdata/loaders/expectedPerson.json"), new TypeReference<>() {});
        assertEquals(expectedPerson, actualPerson);

        List<MemberVotes> memberVotes = memberVotesRepository.findByMemberId(actualMember.getId());
        assertEquals(2386, memberVotes.size());

        Optional<Organ> optionalOrgan = organRepository.findById(actualMember.getPeriodId());
        assertTrue(optionalOrgan.isPresent());

        Organ actualOrgan = optionalOrgan.get();
        Organ expectedOrgan = objectMapper.readValue(
                TestUtils.getResourceContent("testdata/loaders/expectedOrgan.json"), new TypeReference<>() {});
        assertEquals(expectedOrgan, actualOrgan);

        Optional<Meeting> optionalMeeting = meetingRepository.findById(new MeetingAgendaId(MEETING_ID, 0));
        assertTrue(optionalMeeting.isPresent());

        Meeting actualMeeting = optionalMeeting.get();
        Meeting expectedMeeting = objectMapper.readValue(
                TestUtils.getResourceContent("testdata/loaders/expectedMeeting.json"), new TypeReference<>() {});
        assertEquals(expectedMeeting, actualMeeting);

        List<MeetingPoint> actualMeetingPoints = meetingPointRepository.findByMeetingIdAndAgendaType(
                actualMeeting.getId().getId(),
                actualMeeting.getId().getAgendaType()
        );
        List<MeetingPoint> expectedMeetingPoint = objectMapper.readValue(
                TestUtils.getResourceContent("testdata/loaders/expectedMeetingPoints.json"), new TypeReference<>() {});
        assertEquals(expectedMeetingPoint, actualMeetingPoints);
    }
}
