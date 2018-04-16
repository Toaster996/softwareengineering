package de.dhbw.softwareengineering.digitaljournal.business;

import de.dhbw.softwareengineering.digitaljournal.domain.ContactRequest;
import de.dhbw.softwareengineering.digitaljournal.persistence.ContactRequestRepository;
import de.dhbw.softwareengineering.digitaljournal.util.UUIDGenerator;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
public class ContactRequestService {

    private final ContactRequestRepository repository;

    public ContactRequestService(ContactRequestRepository repository) {
        this.repository = repository;
    }


    public void create(@Valid ContactRequest contactRequest) {
        contactRequest.setId(UUIDGenerator.generateUniqueUUID(repository));
        repository.save(contactRequest);
    }

    public List<ContactRequest> findAllUnsolvedRequests() {
        return repository.findAllBySolvedFalse();
    }

    public void solve(ContactRequest contactRequest) {
        contactRequest.setSolved(true);
        repository.save(contactRequest);
    }
}
