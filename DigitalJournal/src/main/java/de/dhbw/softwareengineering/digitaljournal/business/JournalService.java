package de.dhbw.softwareengineering.digitaljournal.business;

import de.dhbw.softwareengineering.digitaljournal.domain.Journal;
import de.dhbw.softwareengineering.digitaljournal.persistence.JournalRepository;
import de.dhbw.softwareengineering.digitaljournal.util.UUIDGenerator;
import de.dhbw.softwareengineering.digitaljournal.util.exceptions.JournalNotFoundException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.unbescape.html.HtmlEscape.escapeHtml5;

@Service
public class JournalService implements AbstractService {

    private final JournalRepository repository;
    private final ImageService imageService;
    private SharedJournalService sharedJournalService;

    public JournalService(JournalRepository repository,@Lazy ImageService imageService, SharedJournalService sharedJournalService) {
        this.repository = repository;
        this.imageService = imageService;
        this.sharedJournalService = sharedJournalService;
    }

    public List<Journal> findAll(String username) {
        List<Journal> journals = repository.findAllByUsernameOrderByDateDesc(username);

        for (Journal j : journals) {
            j.setContent(j.getContent().replaceAll("\n", "<br/>"));
            j.setJournalName(j.getJournalName());
        }

        return journals;
    }

    public Journal save(Journal journal) {
        journal.setJournalid(UUIDGenerator.generateUniqueUUID(repository));
        journal.setContent(escapeHtml5(journal.getContent()));
        journal.setJournalName(escapeHtml5(journal.getJournalName()));
        return repository.save(journal);
    }

    public Journal update(Journal journal) {
        return repository.save(journal);
    }

    public Journal findById(String journalId) throws JournalNotFoundException {
        Optional<Journal> journalOptional = repository.findById(journalId);

        if (journalOptional.isPresent()) {
            Journal journal = journalOptional.get();
            return journal;
        } else {
            throw new JournalNotFoundException("No journal found with Id: " + journalId);
        }
    }

    public void deleteById(String journalId) {
        imageService.deleteAllByJournalId(journalId);
        repository.deleteById(journalId);
        sharedJournalService.deleteByJournalName(journalId);
    }

    public int countByUsername(String username) {
        return repository.countByUsername(username);
    }

    public void deleteAllFromUser(String username) {
        List<Journal> journals = repository.findAllByUsernameOrderByDateDesc(username);
        for (Journal j : journals) {
            deleteById(j.getJournalid());
        }
    }
}
