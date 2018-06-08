package de.dhbw.softwareengineering.digitaljournal.persistence;

import de.dhbw.softwareengineering.digitaljournal.domain.SharedJournal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SharedJournalRepository extends JpaRepository<SharedJournal, String> {
    List<SharedJournal> findAllByCoAuthor(String coAuthor);
    List<SharedJournal> findAllByJournalName(String journalID);
}
