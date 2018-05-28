package de.dhbw.softwareengineering.digitaljournal.persistence;

import de.dhbw.softwareengineering.digitaljournal.domain.Goal;
import de.dhbw.softwareengineering.digitaljournal.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoalRepository extends JpaRepository<Goal, String>{

    List<Goal> findAllByUsernameOrderByDateDesc(String name);
}
