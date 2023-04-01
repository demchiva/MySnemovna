package cz.my.snemovna.jpa.repository.members;

import cz.my.snemovna.jpa.model.members.Organ;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The organ repository.
 */
public interface OrganRepository extends JpaRepository<Organ, Long> {
}
