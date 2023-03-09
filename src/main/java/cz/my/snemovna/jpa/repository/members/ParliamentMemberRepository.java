package cz.my.snemovna.jpa.repository.members;

import cz.my.snemovna.jpa.model.members.ParliamentMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParliamentMemberRepository extends JpaRepository<ParliamentMember, Long> {
}
