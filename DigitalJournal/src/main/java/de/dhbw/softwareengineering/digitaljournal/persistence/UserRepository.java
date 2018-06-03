package de.dhbw.softwareengineering.digitaljournal.persistence;

import de.dhbw.softwareengineering.digitaljournal.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String>{

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    List<User> findAllByUsernameLike(String username);
}
