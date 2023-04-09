package cz.my.snemovna.jpa.repository.members;

import cz.my.snemovna.jpa.model.members.ParliamentMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * The parliament member repository.
 */
public interface ParliamentMemberRepository extends JpaRepository<ParliamentMember, Long> {

    @Query("select pm " +
            "from ParliamentMember pm " +
            "join Person person on pm.personId = person.id " +
            "join Organ party on pm.partyId = party.id " +
            "join Organ region on pm.regionId = region.id " +
            "join Organ period on pm.periodId = period.id " +
            "where lower(person.firstName) like lower(concat(:search, '%')) " + // Strings starts with
            "or lower(person.lastName) like lower(concat(:search, '%')) " +
            "or lower(party.nameCz) like lower(concat(:search, '%')) " +
            "or lower(region.nameCz) like lower(concat(:search, '%')) " +
            "or str(period.dateFrom) like lower(concat(:search, '%')) " +
            "or str(period.dateTo) like lower(concat(:search, '%')) " +
            "or lower(person.firstName) like lower(concat('%', :search)) " + // Strings ends with
            "or lower(person.lastName) like lower(concat('%', :search)) " +
            "or lower(party.nameCz) like lower(concat('%', :search)) " +
            "or lower(region.nameCz) like lower(concat('%', :search)) " +
            "or str(period.dateFrom) like lower(concat('%', :search)) " +
            "or str(period.dateTo) like lower(concat('%', :search))" +
            "or lower(person.firstName) like lower(concat('%', :search, '%')) " + // Strings contains
            "or lower(person.lastName) like lower(concat('%', :search, '%')) " +
            "or lower(party.nameCz) like lower(concat('%', :search, '%')) " +
            "or lower(region.nameCz) like lower(concat('%', :search, '%')) " +
            "or str(period.dateFrom) like lower(concat('%', :search, '%')) " +
            "or str(period.dateTo) like lower(concat('%', :search, '%'))")
    Page<ParliamentMember> findBySearch(@Param("search") String search, Pageable page);
}
