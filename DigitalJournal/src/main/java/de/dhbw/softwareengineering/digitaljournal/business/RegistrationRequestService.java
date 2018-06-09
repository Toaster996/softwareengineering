package de.dhbw.softwareengineering.digitaljournal.business;

import de.dhbw.softwareengineering.digitaljournal.domain.RegistrationRequest;
import de.dhbw.softwareengineering.digitaljournal.persistence.RegistrationRequestRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static de.dhbw.softwareengineering.digitaljournal.util.Constants.HOUR;

@Service
public class RegistrationRequestService implements AbstractService {

    private final RegistrationRequestRepository repository;

    public RegistrationRequestService(RegistrationRequestRepository repository) {
        this.repository = repository;
    }

    public RegistrationRequest create(String username) {
        RegistrationRequest request = new RegistrationRequest();
        request.setUsername(username);
        request.setRegistrationUUID(UUID.randomUUID().toString());
        request.setDate(System.currentTimeMillis());

        return repository.save(request);
    }

    public boolean confirmRequest(String uuid, UserService userService) {
        Optional<RegistrationRequest> request = repository.findByRegistrationUUID(uuid);

        if (request.isPresent()) {
            userService.verify(request.get().getUsername());
            repository.delete(request.get());
            return true;
        } else {
            return false;
        }
    }

    public void deleteOldRequests() {
        repository.deleteByDateBefore(System.currentTimeMillis() - 24 * HOUR);
    }
}
