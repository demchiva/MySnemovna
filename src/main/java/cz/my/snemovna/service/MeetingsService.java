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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeetingsService implements IMeetingsService {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy");
    private static final DateTimeFormatter FORMATTER_HOURS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private static final List<Tuple<List<Integer>, String>> MEETING_POINT_TYPES = List.of(
            Tuple.of(List.of(1, 7, 15, 17, 18, 40, 41), "1. ètení"),
            Tuple.of(List.of(2, 3, 4), "2. ètení"),
            Tuple.of(List.of(5), "3. ètení"),
            Tuple.of(List.of(13), "Vráceno prezidentem"),
            Tuple.of(List.of(14), "Vráceno Senátem"),
            Tuple.of(List.of(10), "Zkrácené jednání")
    );

    private static final int MEETING_POINT_TYPES_INTERPELLATION_ANSWER = 6;

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
                safeParseDateWithHours(meeting.getDateFrom()),
                safeParseDateWithHours(meeting.getDateTo())
        );
    }

    private String getDate(final Meeting meeting, final MeetingState state) {
       return state != null && state.getMeetingStatusText() != null 
               ? state.getMeetingStatusText() 
               : getDateFromMeeting(meeting);
    }

    private String getDateFromMeeting(final Meeting meeting) {
        if (meeting.getDateTo() == null || meeting.getDateTo().isEmpty()) {
            return safeParseDateWithHours(meeting.getDateFrom()).format(DATE_FORMAT);
        }

        return safeParseDateWithHours(meeting.getDateFrom()).format(DATE_FORMAT)
                + " - "
                + safeParseDateWithHours(meeting.getDateTo()).format(DATE_FORMAT);
    }

    @Override
    public MeetingDetailDto getMeeting(@NotNull final Long meetingId, @NotNull final MeetingAgendaType type) {
        final List<MeetingPoint> meetingPoints =
                meetingPointRepository.findByMeetingIdAndAgendaType(meetingId, type.getType());
        final Map<Long, MeetingPointState> states = meetingPointStateRepository
                .findAllById(meetingPoints.stream().map(MeetingPoint::getStateId).toList())
                .stream()
                .collect(Collectors.toMap(MeetingPointState::getId, Function.identity(), (a, b) -> b));

        return new MeetingDetailDto(
                meetingPoints
                        .stream()
                        .filter(e -> !(MEETING_POINT_TYPES_INTERPELLATION_ANSWER == e.getTypeId()))
                        .map(e -> createPointDto(e, states.getOrDefault(e.getStateId(), null)))
                        .toList()
        );
    }

    private MeetingDetailDto.MeetingPointDto createPointDto(final MeetingPoint point, final MeetingPointState state) {
        return new MeetingDetailDto.MeetingPointDto(
                point.getFullName(),
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

    protected LocalDateTime safeParseDateWithHours(String value) {
        return value == null || value.isEmpty() ? null : LocalDateTime.parse(value, FORMATTER_HOURS);
    }
}
