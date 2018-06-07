package de.dhbw.softwareengineering.digitaljournal.persistence;

import de.dhbw.softwareengineering.digitaljournal.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UploadImageRepository extends JpaRepository<Image, String> {

    List<Image> findAllByJournalid(String journal_id);
}
