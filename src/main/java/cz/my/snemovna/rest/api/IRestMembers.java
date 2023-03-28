package cz.my.snemovna.rest.api;

import cz.my.snemovna.dto.members.MemberDetailDto;
import cz.my.snemovna.dto.members.MemberDto;
import cz.my.snemovna.dto.members.MemberVotesDto;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    @GET
    @Path("/")
    Page<MemberDto> getMembers(@QueryParam("search") @Nullable String search, Pageable page);

    /**
     * Get member detail.
     * @param memberId member id.
     * @return the member detail.
     */
    @GET
    @Path("/{memberId}")
    MemberDetailDto getMember(@PathParam("memberId") Long memberId);

    /**
     * Get info how given member of parliament voted.
     * @param memberId member id.
     * @return the list of parliament member votes.
     */
    @GET
    @Path("/{memberId}/votes")
    List<MemberVotesDto> getVotes(@PathParam("memberId") Long memberId);
}
