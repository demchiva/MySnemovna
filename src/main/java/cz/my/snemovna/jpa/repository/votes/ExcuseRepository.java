package cz.my.snemovna.jpa.repository.votes;

import cz.my.snemovna.jpa.model.votes.Excuse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExcuseRepository extends JpaRepository<Excuse, Long> {
}
