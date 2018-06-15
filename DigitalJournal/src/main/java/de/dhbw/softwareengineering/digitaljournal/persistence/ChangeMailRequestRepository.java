package de.dhbw.softwareengineering.digitaljournal.persistence;

import de.dhbw.softwareengineering.digitaljournal.domain.ChangeMailRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ChangeMailRequestRepository extends JpaRepository<ChangeMailRequest, String> {

    Optional<ChangeMailRequest> findByOldmailid(String oldmailid);

    Optional<ChangeMailRequest> findByNewmailid(String newmailid);

    @Transactional
    void deleteByDateBefore(long deletionDate);
}
