package de.dhbw.softwareengineering.digitaljournal.persistence;

import de.dhbw.softwareengineering.digitaljournal.domain.DeleteAccountRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface DeleteAccountRequestRepository extends JpaRepository<DeleteAccountRequest, String> {

    boolean existsByUsername(String username);

    @Transactional
    void deleteByDateBefore(long deletionDate);
}
