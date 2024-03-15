package cz.my.snemovna.rest.api;

import cz.my.snemovna.dto.members.MemberDetailDto;
import cz.my.snemovna.dto.members.MemberDto;
import cz.my.snemovna.dto.members.MemberVotesDto;
import cz.my.snemovna.rest.PageApiRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
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
import java.util.List;

/**
 * The class for manage members agenda.
 */
@Tag(name = "Members", description = "Endpoints for manage members agenda.")
@Path("/api/v1/members")
@Consumes(MediaType.APPLICATION_JSON_VALUE)
@Produces(MediaType.APPLICATION_JSON_VALUE)
public interface IRestMembers {

    /**
     * Get the members for members listing.
     * @param page the page.
     * @param search param for fulltext search in results. If null no search applied.
     * @return the members page.
     */
    @Operation(summary = "Get the members for members listing.")
    @GET
    @Path("/")
    Page<MemberDto> getMembers(@QueryParam("search") @Nullable String search, @ParameterObject @Valid PageApiRequest page);

    /**
     * Get member detail.
     * @param memberId member id.
     * @return the member detail.
     */
    @Operation(summary = "Get member detail.")
    @GET
    @Path("/{memberId}")
    MemberDetailDto getMember(@PathParam("memberId") @NotNull Long memberId);

    /**
     * Get info how given member of parliament voted.
     * @param memberId member id.
     * @return the list of parliament member votes.
     */
    @Operation(summary = "Get info how given member of parliament voted.")
    @GET
    @Path("/{memberId}/votes")
    List<MemberVotesDto> getVotes(@PathParam("memberId") @NotNull Long memberId);
}
