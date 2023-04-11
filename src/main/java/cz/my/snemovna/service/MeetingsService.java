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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class MeetingsService implements IMeetingsService {

    private static final String EMPTY_VALUE = "\\";

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter FORMATTER_HOURS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private static final List<Tuple<List<Integer>, String>> MEETING_POINT_TYPES = List.of(
            Tuple.of(List.of(1, 7, 15, 17, 18, 40, 41), "1. čtení"),
            Tuple.of(List.of(2, 3, 4), "2. čtení"),
            Tuple.of(List.of(5), "3. čtení"),
            Tuple.of(List.of(13), "Vráceno prezidentem"),
            Tuple.of(List.of(14), "Vráceno Senátem"),
            Tuple.of(List.of(10), "Zkrácené jednání")
    );

    private static final Integer DEFAULT_MEETING_TYPE = 1;
    private static final Map<Integer, String> MEETING_TYPES = Map.of(
            1, "Řádná",
            2, "Mimořádná"
    );

    private static final int MEETING_POINT_TYPES_INTERPELLATION_ANSWER = 6;

    private final MeetingPointRepository meetingPointRepository;
    private final MeetingPointStateRepository meetingPointStateRepository;
    private final MeetingStateRepository meetingStateRepository;
    private final MeetingRepository meetingRepository;
    private final OrganRepository organRepository;

    @Override
    public Page<MeetingDto> getMeetings(@NotNull final Pageable page) {
        final Page<Meeting> meetings = getMeetingsInternal(page);
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

    private Page<Meeting> getMeetingsInternal(@NotNull final Pageable page) {
        final Page<Meeting> meetings = meetingRepository.findAll(page);
        final Map<MeetingAgendaId, Meeting> meetingMap = meetings
                .stream()
                .collect(Collectors.toMap(Meeting::getId, Function.identity(), (a, b) -> b));
        final List<Meeting> filteredMeetings = meetings
                .stream()
                .filter(e -> MeetingAgendaType.APPROVED.getType() == e.getId().getAgendaType() || !meetingMap
                        .containsKey(new MeetingAgendaId(e.getId().getId(), MeetingAgendaType.APPROVED.getType())))
                .toList();
        return new PageImpl<>(filteredMeetings, page, filteredMeetings.size());
    }

    private MeetingDto createMeetingDto(final Meeting meeting, @Nullable final MeetingState state, final Organ organ) {
        return new MeetingDto(
                meeting.getId().getId(),
                meeting.getMeetingNumber(),
                getState(state),
                getDate(meeting, state),
                ofNullable(state)
                        .map(MeetingState::getType)
                        .map(MEETING_TYPES::get)
                        .orElse(MEETING_TYPES.get(DEFAULT_MEETING_TYPE)),
                organ.getShortName(),
                safeParseDateWithHours(meeting.getDateFrom()),
                safeParseDateWithHours(meeting.getDateTo())
        );
    }

    private String getDate(final Meeting meeting, final MeetingState state) {
       return state != null
               && state.getMeetingBeginText() != null
               && !state.getMeetingBeginText().isEmpty()
               && !EMPTY_VALUE.equals(state.getMeetingBeginText().trim())
               ? state.getMeetingBeginText()
               : getDateFromMeeting(meeting);
    }

    private String getDateFromMeeting(final Meeting meeting) {
        if (meeting.getDateTo() == null || meeting.getDateTo().isEmpty()) {
            return safeParseDateWithHours(meeting.getDateFrom()).format(DATE_FORMAT);
        }

        return safeParseDateWithHours(meeting.getDateFrom()).format(DATE_FORMAT)
                + "-"
                + safeParseDateWithHours(meeting.getDateTo()).format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    private String getState(final MeetingState state) {
        if (state == null || state.getMeetingStatusText2() == null
                || state.getMeetingStatusText2().isEmpty()
                || EMPTY_VALUE.equals(state.getMeetingStatusText2().trim())) {
            return null;
        }

        return state.getMeetingStatusText2();
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
                        .filter(e -> MEETING_POINT_TYPES_INTERPELLATION_ANSWER != e.getTypeId())
                        .map(e -> createPointDto(e, states.getOrDefault(e.getStateId(), null)))
                        .toList()
        );
    }

    private MeetingDetailDto.MeetingPointDto createPointDto(final MeetingPoint point, final MeetingPointState state) {
        return new MeetingDetailDto.MeetingPointDto(
                point.getFullName(),
                state != null ? capitalize(state.getDescription().trim()) : null,
                getType(point)
        );
    }

    public static String capitalize(final String str) {
        if (str == null || str.length() == 0) {
            return str;
        }

        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    @Override
    public String getType(final MeetingPoint point) {
        return MEETING_POINT_TYPES
                .stream()
                .filter(e -> e.x().contains(point.getTypeId()))
                .map(Tuple::y)
                .findFirst()
                .orElse(null);
    }

    protected LocalDateTime safeParseDateWithHours(String value) {
        return value == null || value.isEmpty() ? null : LocalDateTime.parse(value, FORMATTER_HOURS);
    }
}
