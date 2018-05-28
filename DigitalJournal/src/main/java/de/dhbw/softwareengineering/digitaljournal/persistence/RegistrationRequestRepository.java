package de.dhbw.softwareengineering.digitaljournal.persistence;

import de.dhbw.softwareengineering.digitaljournal.domain.RegistrationRequest;
import de.dhbw.softwareengineering.digitaljournal.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface RegistrationRequestRepository extends JpaRepository<RegistrationRequest, String>{

    Optional<RegistrationRequest> findByRegistrationUUID(String registrationUUID);

    @Transactional
    void deleteByDateBefore(long deletionDate);
}
