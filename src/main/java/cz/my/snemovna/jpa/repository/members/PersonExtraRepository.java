package cz.my.snemovna.jpa.repository.members;

import cz.my.snemovna.jpa.model.members.PersonExtra;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The additional person information repository.
 */
public interface PersonExtraRepository extends JpaRepository<PersonExtra, Long> {
}
