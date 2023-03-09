package cz.my.snemovna.jpa.repository.members;

import cz.my.snemovna.jpa.model.members.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
}
