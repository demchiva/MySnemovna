package cz.my.snemovna.jpa.repository.members;

import cz.my.snemovna.jpa.model.members.RegionalOffice;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The regional office repository.
 */
public interface RegionalOfficeRepository extends JpaRepository<RegionalOffice, Long> {
}
