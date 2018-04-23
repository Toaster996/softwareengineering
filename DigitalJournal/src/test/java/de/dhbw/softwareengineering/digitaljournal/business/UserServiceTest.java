package de.dhbw.softwareengineering.digitaljournal.business;

import de.dhbw.softwareengineering.digitaljournal.TestingData;
import de.dhbw.softwareengineering.digitaljournal.domain.User;
import de.dhbw.softwareengineering.digitaljournal.domain.form.RegistrationUser;
import de.dhbw.softwareengineering.digitaljournal.persistence.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Before
    public void setUp() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(BCryptPasswordEncoder.class);
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    public void existByUsername() {
        User user = TestingData.createUser(true);
        when(userRepository.existsById(user.getUsername())).thenReturn(true);

        assertTrue(userService.existByUsername(user.getUsername()));
    }

    @Test
    public void existByEmail() {
        User user = TestingData.createUser(true);
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        assertTrue(userService.existByEmail(user.getEmail()));
    }

    @Test
    public void findByUsernameSuccess() {
        User user = TestingData.createUser(true);
        when(userRepository.findById(user.getUsername())).thenReturn(Optional.of(user));

        assertEquals(userService.findByName(user.getUsername()), user);
    }

    @Test(expected = RuntimeException.class)
    public void findByUsernameFail() {
        userService.findByName("no_actual_user_will_be_found_by_id");
    }

    @Test
    public void findByEmailSuccess() {
        User user = TestingData.createUser(true);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        assertEquals(userService.findByEmail(user.getEmail()), user);
    }

    @Test(expected = RuntimeException.class)
    public void findByEmailFail() {
        userService.findByEmail("no_actual_user_will_be_found_by_mail");
    }

    @Test
    public void create() {
        User user = TestingData.createUser(true);
        RegistrationUser registrationUser = TestingData.createRegistrationUser();

        when(userRepository.save(any(User.class))).thenReturn(user);

        User returned = userService.create(registrationUser);

        assertEquals(user.getUsername(), returned.getUsername());
    }

    @Test
    public void verifySuccess() {
        User user = TestingData.createUser(false);
        when(userRepository.findById(user.getUsername())).thenReturn(Optional.of(user));

        userService.verify(user.getUsername());

        assertTrue(user.isVerified());
    }

    @Test
    public void verifyFail() {
        User user = TestingData.createUser(false);
        when(userRepository.findById(user.getUsername())).thenReturn(Optional.of(user));

        userService.verify("not_a_valid_string");

        assertFalse(user.isVerified());
    }

    @Test
    public void update() {
        User user = TestingData.createUser(true);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User returned = userService.update(user);
        assertEquals(user, returned);
    }

}