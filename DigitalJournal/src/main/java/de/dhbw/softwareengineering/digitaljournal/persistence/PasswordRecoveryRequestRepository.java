package de.dhbw.softwareengineering.digitaljournal.persistence;

import de.dhbw.softwareengineering.digitaljournal.domain.PasswordRecoveryRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface PasswordRecoveryRequestRepository extends JpaRepository<PasswordRecoveryRequest, String>{

    void deleteAllByUsername(String username);

    @Transactional
    void deleteByCreationDateBefore(long deletionDate);
}
