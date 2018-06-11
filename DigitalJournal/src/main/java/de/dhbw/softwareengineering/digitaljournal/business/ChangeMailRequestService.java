package de.dhbw.softwareengineering.digitaljournal.business;

import de.dhbw.softwareengineering.digitaljournal.domain.ChangeMailRequest;
import de.dhbw.softwareengineering.digitaljournal.domain.User;
import de.dhbw.softwareengineering.digitaljournal.persistence.ChangeMailRequestRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static de.dhbw.softwareengineering.digitaljournal.util.Constants.HOUR;

@Service
public class ChangeMailRequestService implements AbstractService {

    private final ChangeMailRequestRepository repository;

    public ChangeMailRequestService(ChangeMailRequestRepository repository) {
        this.repository = repository;
    }

    public ChangeMailRequest create(String username, String newmail) {
        if (repository.existsById(username))
            return null;

        ChangeMailRequest request = new ChangeMailRequest();
        request.setUsername(username);
        request.setDate(System.currentTimeMillis());
        request.setNewmail(newmail);
        request.setNewmailid(UUID.randomUUID().toString());
        request.setOldmailid(UUID.randomUUID().toString());

        return repository.save(request);
    }

    private String confirmOldMail(String oldmailid) {
        Optional<ChangeMailRequest> request = repository.findByOldmailid(oldmailid);

        if (request.isPresent()) {
            request.get().setOldconfirmed(true);
            repository.save(request.get());
            return request.get().getUsername();
        }

        return null;
    }

    private String confirmNewMail(String newmailid) {
        Optional<ChangeMailRequest> request = repository.findByNewmailid(newmailid);

        if (request.isPresent()) {
            request.get().setNewconfirmed(true);
            repository.save(request.get());
            return request.get().getUsername();
        }

        return null;
    }

    public boolean[] isConfirmed(String username) {
        Optional<ChangeMailRequest> request = repository.findById(username);

        if (!request.isPresent()) {
            return new boolean[]{false, false};
        } else {
            ChangeMailRequest mailRequest = request.get();
            return new boolean[]{mailRequest.isOldconfirmed(), mailRequest.isNewconfirmed()};
        }
    }

    public void deleteOldRequests() {
        repository.deleteByDateBefore(System.currentTimeMillis() - HOUR);
    }

    public String confirm(String id) {
        String username = confirmOldMail(id);
        if (username == null) {
            username = confirmNewMail(id);
        }

        return username;
    }

    public String getNewMail(String username) {
        Optional<ChangeMailRequest> request = repository.findById(username);
        if (request.isPresent()) {
            return request.get().getNewmail();
        }

        return null;
    }

    public void delete(String username) {
        repository.deleteById(username);
    }

    public boolean hasRequest(String username) {
        return repository.existsById(username);
    }
}
