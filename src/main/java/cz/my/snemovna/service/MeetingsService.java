package cz.my.snemovna.service;

import com.google.cloud.Tuple;
import cz.my.snemovna.dto.meetings.MeetingAgendaType;
import cz.my.snemovna.dto.meetings.MeetingDetailDto;
import cz.my.snemovna.dto.meetings.MeetingDto;
import cz.my.snemovna.jpa.model.meetings.Meeting;
import cz.my.snemovna.jpa.model.meetings.MeetingAgendaId;
import cz.my.snemovna.jpa.model.meetings.MeetingPoint;
import cz.my.snemovna.jpa.model.meetings.MeetingPointState;
import cz.my.snemovna.jpa.model.meetings.MeetingState;
import cz.my.snemovna.jpa.model.members.Organ;
import cz.my.snemovna.jpa.repository.meetings.MeetingPointRepository;
import cz.my.snemovna.jpa.repository.meetings.MeetingPointStateRepository;
import cz.my.snemovna.jpa.repository.meetings.MeetingRepository;
import cz.my.snemovna.jpa.repository.meetings.MeetingStateRepository;
import cz.my.snemovna.jpa.repository.members.OrganRepository;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeetingsService implements IMeetingsService {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy");

    private static final List<Tuple<List<Integer>, String>> MEETING_POINT_TYPES = List.of(
            Tuple.of(List.of(1, 7, 15, 17, 18, 40, 41), "1. �ten�"),
            Tuple.of(List.of(2, 3, 4), "2. �ten�"),
            Tuple.of(List.of(5), "3. �ten�"),
            Tuple.of(List.of(13), "Vr�ceno prezidentem"),
            Tuple.of(List.of(14), "Vr�ceno Sen�tem"),
            Tuple.of(List.of(10), "Zkr�cen� jedn�n�")
    );

    private final MeetingPointRepository meetingPointRepository;
    private final MeetingPointStateRepository meetingPointStateRepository;
    private final MeetingStateRepository meetingStateRepository;
    private final MeetingRepository meetingRepository;
    private final OrganRepository organRepository;

    @Override
    public Page<MeetingDto> getMeetings(@NotNull Pageable page) {
        final Page<Meeting> meetings = meetingRepository.findAll(page);
        final Map<Long, MeetingState> states = meetingStateRepository
                .findAllById(meetings.map(Meeting::getId).map(MeetingAgendaId::getId))
                .stream()
                .collect(Collectors.toMap(MeetingState::getMeetingId, Function.identity()));
        final Map<Long, Organ> organs = organRepository
                .findAllById(meetings.map(Meeting::getOrganId).stream().distinct().toList())
                .stream()
                .collect(Collectors.toMap(Organ::getId, Function.identity()));
        return meetings.map(e -> createMeetingDto(e, states.get(e.getId().getId()), organs.get(e.getOrganId())));
    }

    private MeetingDto createMeetingDto(final Meeting meeting, @Nullable final MeetingState state, final Organ organ) {
        return new MeetingDto(
                meeting.getId().getId(),
                meeting.getMeetingNumber(),
                state != null ? state.getState() : null,
                getDate(meeting, state),
                state != null ? state.getType() : null,
                organ.getShortName(),
                meeting.getDateFrom(),
                meeting.getDateTo()
        );
    }

    private String getDate(final Meeting meeting, final MeetingState state) {
       return state != null && state.getMeetingStatusText() != null 
               ? state.getMeetingStatusText() 
               : getDateFromMeeting(meeting);
    }

    private String getDateFromMeeting(final Meeting meeting) {
        if (meeting.getDateTo() == null) {
            return meeting.getDateFrom().format(DATE_FORMAT);
        }

        return meeting.getDateFrom().format(DATE_FORMAT) + " - " + meeting.getDateTo().format(DATE_FORMAT);
    }

    @Override
    public MeetingDetailDto getMeeting(@NotNull final Long meetingId, @NotNull final MeetingAgendaType type) {
        final List<MeetingPoint> meetingPoints =
                meetingPointRepository.findAllById(List.of(new MeetingAgendaId(meetingId, type.getType())));
        final Map<Long, MeetingPointState> states = meetingPointStateRepository
                .findAllById(meetingPoints.stream().map(MeetingPoint::getStateId).toList())
                .stream()
                .collect(Collectors.toMap(MeetingPointState::getId, Function.identity()));

        return new MeetingDetailDto(
                meetingPoints
                        .stream()
                        .map(e -> createPointDto(e, states.getOrDefault(e.getStateId(), null)))
                        .toList()
        );
    }

    private MeetingDetailDto.MeetingPointDto createPointDto(final MeetingPoint point, final MeetingPointState state) {
        return new MeetingDetailDto.MeetingPointDto(
                point.getShortName(),
                state != null ? state.getDescription() : null,
                getType(point)
        );
    }

    private String getType(final MeetingPoint point) {
        return MEETING_POINT_TYPES
                .stream()
                .filter(e -> e.x().contains(point.getType()))
                .map(Tuple::y)
                .findFirst()
                .orElse(null);
    }
}