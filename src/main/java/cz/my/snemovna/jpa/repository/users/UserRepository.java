package cz.my.snemovna.jpa.repository.users;

import cz.my.snemovna.jpa.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * The application user repository.
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
