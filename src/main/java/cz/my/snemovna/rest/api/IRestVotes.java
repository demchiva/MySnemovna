package cz.my.snemovna.rest.api;

import cz.my.snemovna.dto.votes.VoteDetailDto;
import cz.my.snemovna.dto.votes.VoteDto;
import cz.my.snemovna.dto.votes.VoteMembersDto;
import cz.my.snemovna.rest.PageApiRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

/**
 * The class for manage votes agenda.
 */
@Tag(name = "Votes", description = "Endpoints for manage votes agenda.")
@Path("/api/v1/votes")
@Consumes(MediaType.APPLICATION_JSON_VALUE)
@Produces(MediaType.APPLICATION_JSON_VALUE)
public interface IRestVotes {

    /**
     * Get the votes for votes listing.
     * @param page the page
     * @return the votes page.
     */
    @Operation(summary = "Get the votes for votes listing.")
    @GET
    @Path("/")
    Page<VoteDto> getVotes(@ParameterObject PageApiRequest page);

    /**
     * Get vote detail.
     * @param voteId vote id.
     * @return the vote detail.
     */
    @Operation(summary = "Get vote detail.")
    @GET
    @Path("/{voteId}")
    VoteDetailDto getVote(@PathParam("voteId") Long voteId);

    /**
     * Get info how members of parliament voted in the given vote.
     * @param voteId vote id.
     * @return the list of parliament member votes.
     */
    @Operation(summary = "Get info how members of parliament voted in the given vote.")
    @GET
    @Path("/{voteId}/members")
    List<VoteMembersDto> getMembers(@PathParam("voteId") Long voteId);
}
