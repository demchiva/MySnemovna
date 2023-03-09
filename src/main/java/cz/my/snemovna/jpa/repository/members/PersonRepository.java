package cz.my.snemovna.jpa.repository.members;

import cz.my.snemovna.jpa.model.members.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
