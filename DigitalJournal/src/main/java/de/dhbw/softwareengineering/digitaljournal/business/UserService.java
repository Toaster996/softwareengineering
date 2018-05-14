package de.dhbw.softwareengineering.digitaljournal.business;

import de.dhbw.softwareengineering.digitaljournal.domain.User;
import de.dhbw.softwareengineering.digitaljournal.domain.form.RegistrationUser;
import de.dhbw.softwareengineering.digitaljournal.persistence.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService extends AbstractService{

    private final UserRepository repository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository repository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.repository = repository;
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
            throw new RuntimeException("No user found with name: " + username);
        }
    }

    public User findByEmail(String email) {
        Optional<User> userOptional = repository.findByEmail(email);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new RuntimeException("No user found with email: " + email);
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
}
