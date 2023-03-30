package cz.my.snemovna.rest.api;

import cz.my.snemovna.dto.meetings.MeetingAgendaType;
import cz.my.snemovna.dto.meetings.MeetingDetailDto;
import cz.my.snemovna.dto.meetings.MeetingDto;
import cz.my.snemovna.rest.PageApiRequest;
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
@Path("/api/v1/meetings")
@Consumes(MediaType.APPLICATION_JSON_VALUE)
@Produces(MediaType.APPLICATION_JSON_VALUE)
public interface IRestMeetings {

    /**
     * Get the meetings for meetings listing.
     * @param page the page.
     * @return the meetings page.
     */
    @GET
    @Path("/")
    Page<MeetingDto> getMeetings(PageApiRequest page);

    /**
     * Get meeting detail.
     * @param meetingId meeting id.
     * @return the meeting detail.
     */
    @GET
    @Path("/{meetingId}")
    MeetingDetailDto getMeeting(@PathParam("meetingId") Long meetingId, @QueryParam("type") MeetingAgendaType type);
}
