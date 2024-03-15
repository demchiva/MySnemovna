package cz.my.snemovna.rest.api;

import cz.my.snemovna.dto.meetings.MeetingAgendaType;
import cz.my.snemovna.dto.meetings.MeetingDetailDto;
import cz.my.snemovna.dto.meetings.MeetingDto;
import cz.my.snemovna.rest.PageApiRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

/**
 * The class for manage meetings agenda.
 */
@Tag(name = "Meetings", description = "Endpoints for manage meetings agenda.")
@Path("/api/v1/meetings")
@Consumes(MediaType.APPLICATION_JSON_VALUE)
@Produces(MediaType.APPLICATION_JSON_VALUE)
public interface IRestMeetings {

    /**
     * Get the meetings for meetings listing.
     * @param page the page.
     * @return the meetings page.
     */
    @Operation(summary = "Get the meetings for meetings listing")
    @GET
    @Path("/")
    Page<MeetingDto> getMeetings(@ParameterObject @Valid PageApiRequest page);

    /**
     * Get meeting detail.
     * @param meetingId meeting id.
     * @return the meeting detail.
     */
    @Operation(summary = "Get meeting detail.")
    @GET
    @Path("/{meetingId}")
    MeetingDetailDto getMeeting(@PathParam("meetingId") @NotNull Long meetingId, @QueryParam("type") @NotNull MeetingAgendaType type);
}
