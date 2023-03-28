package cz.my.snemovna.service;

import cz.my.snemovna.dto.members.MemberDetailDto;
import cz.my.snemovna.dto.members.MemberDto;
import cz.my.snemovna.dto.members.MemberVotesDto;
import cz.my.snemovna.jpa.model.members.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * The service for manage members agenda.
 */
public interface IMembersService {

    /**
     * The method get members list.
     * @param search param for fulltext search in results. If null no search applied.
     * @param page the page
     * @return the members page.
     */
    Page<MemberDto> getMembers(String search, Pageable page);

    /**
     * The method get the full name with titles of person.
     * @param person the person.
     * @return the full name with titles.
     */
    String getFullNameWithTitles(Person person);

    /**
     * The method get member detail.
     * @param memberId member id.
     * @return the member detail.
     */
    MemberDetailDto getMember(Long memberId);

    /**
     * The method get info how given member of parliament voted.
     * @param memberId member id.
     * @return the list of parliament member votes.
     */
    List<MemberVotesDto> getVotes(Long memberId);
}
