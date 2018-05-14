package de.dhbw.softwareengineering.digitaljournal.business;

import de.dhbw.softwareengineering.digitaljournal.domain.Journal;
import de.dhbw.softwareengineering.digitaljournal.persistence.JournalRepository;
import de.dhbw.softwareengineering.digitaljournal.util.UUIDGenerator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.unbescape.html.HtmlEscape.escapeHtml5;

@Service
public class JournalService extends AbstractService {

    private final JournalRepository repository;

    public JournalService(JournalRepository repository) {
        this.repository = repository;
    }

    public List<Journal> findAll(String username) {
        List<Journal> journals = repository.findAllByUsernameOrderByDateDesc(username);

        for (Journal j : journals) {
            j.setContent(escapeHtml5(j.getContent()).replaceAll("\n", "<br/>"));
            j.setJournalName(escapeHtml5(j.getJournalName()));
        }

        return journals;
    }

    public Journal save(Journal journal) {
        journal.setJournalid(UUIDGenerator.generateUniqueUUID(repository));
        return repository.save(journal);
    }

    public Journal update(Journal journal) {
        return repository.save(journal);
    }

    public Journal findById(String journalId) {
        Optional<Journal> journalOptional = repository.findById(journalId);

        if(journalOptional.isPresent()){
            return journalOptional.get();
        }else {
            throw new RuntimeException("No journal found with Id: " + journalId);
        }
    }

    public void deleteById(String journalId) {
        repository.deleteById(journalId);
    }

    public int countByUsername(String username) {
        return repository.countByUsername(username);
    }
}
