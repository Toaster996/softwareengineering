package de.dhbw.softwareengineering.digitaljournal.business;

import de.dhbw.softwareengineering.digitaljournal.domain.SharedJournal;
import de.dhbw.softwareengineering.digitaljournal.persistence.SharedJournalRepository;
import de.dhbw.softwareengineering.digitaljournal.util.UUIDGenerator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SharedJournalService implements AbstractService {
    private final SharedJournalRepository sharedJournalRepository;

    public SharedJournalService(SharedJournalRepository sharedJournalRepository) {
        this.sharedJournalRepository = sharedJournalRepository;
    }

    public List<SharedJournal> findAllSharedJournalsByName(String name) {
        return sharedJournalRepository.findAllByCoAuthor(name);
    }

    public void save(SharedJournal sharedJournal) {
        sharedJournal.setId(UUIDGenerator.generateUniqueUUID(sharedJournalRepository));
        sharedJournalRepository.save(sharedJournal);
    }

    public List<SharedJournal> findAllByJournalID(String journalID) {
        return sharedJournalRepository.findAllByJournalName(journalID);
    }

}
