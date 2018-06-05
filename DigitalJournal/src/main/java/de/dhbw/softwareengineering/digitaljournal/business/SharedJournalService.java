package de.dhbw.softwareengineering.digitaljournal.business;

import de.dhbw.softwareengineering.digitaljournal.domain.SharedJournal;
import de.dhbw.softwareengineering.digitaljournal.persistence.SharedJournalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SharedJournalService extends AbstractService{
    private final SharedJournalRepository sharedJournalRepository;

    public SharedJournalService(SharedJournalRepository sharedJournalRepository) {
        this.sharedJournalRepository = sharedJournalRepository;
    }

    public List<SharedJournal> findAllSharedJournals(String name){
        return sharedJournalRepository.findAllByCoAuthor(name);
    }


}
