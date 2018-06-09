package de.dhbw.softwareengineering.digitaljournal.business;

import de.dhbw.softwareengineering.digitaljournal.domain.DeleteAccountRequest;
import de.dhbw.softwareengineering.digitaljournal.domain.User;
import de.dhbw.softwareengineering.digitaljournal.domain.form.RegistrationUser;
import de.dhbw.softwareengineering.digitaljournal.persistence.UserRepository;
import de.dhbw.softwareengineering.digitaljournal.util.exceptions.UserNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements AbstractService {

    private final UserRepository repository;
    private final JournalService journalService;
    private final FriendService friendService;
    private final GoalService goalService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository repository, JournalService journalService, FriendService friendService, GoalService goalService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.repository = repository;
        this.journalService = journalService;
        this.friendService = friendService;
        this.goalService = goalService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public boolean existByUsername(String username) {
        return repository.existsById(username);
    }

    public boolean existByEmail(String email) {
        return repository.existsByEmail(email);
    }


    public User findByName(String username) {
        Optional<User> userOptional = repository.findById(username);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new UserNotFoundException(username);
        }
    }

    public boolean exists(String username) {
        Optional<User> userOptional = repository.findById(username);
        return userOptional.isPresent();
    }

    public User findByEmail(String email) {
        Optional<User> userOptional = repository.findByEmail(email);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new UserNotFoundException(email);
        }
    }

    public User create(RegistrationUser registrationUser) {
        User user = new User();
        user.setUsername(registrationUser.getName());
        user.setEmail(registrationUser.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(registrationUser.getPassword()));
        user.setRegistrationDate(System.currentTimeMillis());
        user.setVerified(false);

        return repository.save(user);
    }

    public void verify(String username) {
        Optional<User> userOptional = repository.findById(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setVerified(true);

            repository.save(user);
        }
    }

    public User update(User user) {
        return repository.save(user);
    }

    public List<String> findSuggestionsByName(String username) {
        List<String> names = new ArrayList<>();
        List<User> suggestions = repository.findAllByUsernameLike(username + "%");
        for (User user : suggestions) {
            names.add(user.getUsername());
        }
        return names;
    }

    public void deleteAccount(DeleteAccountRequest request) {
        String username = request.getUsername();
        goalService.deleteAllFromUser(username);
        journalService.deleteAllFromUser(username);
        friendService.deleteAllFromUser(username);
        repository.deleteById(username);
    }
}
