package de.dhbw.softwareengineering.digitaljournal.business;

import de.dhbw.softwareengineering.digitaljournal.domain.PasswordRecoveryRequest;
import de.dhbw.softwareengineering.digitaljournal.domain.User;
import de.dhbw.softwareengineering.digitaljournal.persistence.PasswordRecoveryRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static de.dhbw.softwareengineering.digitaljournal.util.Constants.HOUR;

@Service
public class PasswordRecoveryRequestService {

    private final PasswordRecoveryRequestRepository repository;

    public PasswordRecoveryRequestService(PasswordRecoveryRequestRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void deleteAllByUsername(String username) {
        repository.deleteAllByUsername(username);
    }

    public PasswordRecoveryRequest create(User user) {
        PasswordRecoveryRequest recoveryRequest = new PasswordRecoveryRequest();
                                recoveryRequest.setUsername(user.getUsername());
                                recoveryRequest.setRecoveryUUID(UUID.randomUUID().toString());
                                recoveryRequest.setCreationDate(System.currentTimeMillis());

        return repository.save(recoveryRequest);
    }

    public PasswordRecoveryRequest findByUUID(String uuid) {
        Optional<PasswordRecoveryRequest> request = repository.findById(uuid);

        if(request.isPresent()){
            return request.get();
        }else {
            throw new RuntimeException("No recovery request found with ID: " + uuid);
        }
    }

    public void deleteOldRequests() {
        repository.deleteByCreationDateBefore(System.currentTimeMillis() - HOUR);
    }
}
