package de.dhbw.softwareengineering.digitaljournal.business;

import de.dhbw.softwareengineering.digitaljournal.domain.DeleteAccountRequest;
import de.dhbw.softwareengineering.digitaljournal.domain.User;
import de.dhbw.softwareengineering.digitaljournal.persistence.DeleteAccountRequestRepository;
import de.dhbw.softwareengineering.digitaljournal.util.UUIDGenerator;
import de.dhbw.softwareengineering.digitaljournal.util.exceptions.DeleteAccountRequestException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static de.dhbw.softwareengineering.digitaljournal.util.Constants.HOUR;

@Service
public class DeleteAccountRequestService implements AbstractService {

    private final DeleteAccountRequestRepository repository;

    public DeleteAccountRequestService(DeleteAccountRequestRepository repository) {
        this.repository = repository;
    }

    public DeleteAccountRequest create(User user) {
        DeleteAccountRequest request = new DeleteAccountRequest();
        request.setDate(System.currentTimeMillis());
        request.setUsername(user.getUsername());
        request.setRequestid(UUIDGenerator.generateUniqueUUID(repository));

        return repository.save(request);
    }

    public DeleteAccountRequest findByUUID(String uuid) {
        Optional<DeleteAccountRequest> request = repository.findById(uuid);

        if (request.isPresent()) {
            return request.get();
        } else {
            throw new DeleteAccountRequestException(uuid);
        }
    }

    public void deleteOldRequests() {
        repository.deleteByDateBefore(System.currentTimeMillis() - HOUR);
    }

    public void deleteRequest(DeleteAccountRequest request) {
        repository.delete(request);
    }

    public boolean hasDeletionRequest(User user) {
        return repository.existsByUsername(user.getUsername());
    }
}
