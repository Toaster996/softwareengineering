package de.dhbw.softwareengineering.digitaljournal.persistence;

import de.dhbw.softwareengineering.digitaljournal.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UploadImageRepository extends JpaRepository<Image, String> {

    List<Image> findAllByJournalid(String journalId);

    @Transactional
    void deleteAllByJournalid(String journalId);
}
