package de.dhbw.softwareengineering.digitaljournal.business;

import de.dhbw.softwareengineering.digitaljournal.TestingData;
import de.dhbw.softwareengineering.digitaljournal.domain.RegistrationRequest;
import de.dhbw.softwareengineering.digitaljournal.persistence.RegistrationRequestRepository;
import de.dhbw.softwareengineering.digitaljournal.util.exceptions.DeleteAccountRequestException;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RegistrationRequestServiceTest {

    private RegistrationRequestService service;
    private RegistrationRequestRepository repository;
    private UserService userService;

    @Before
    public void setUp() {
        repository = mock(RegistrationRequestRepository.class);
        service = new RegistrationRequestService(repository);

        userService = mock(UserService.class);
    }

    @Test
    public void create() {
        RegistrationRequest request = TestingData.createRegistrationRequest(TestingData.createUser(false));
        when(repository.save(any(RegistrationRequest.class))).thenReturn(request);

        assertEquals(service.create("user"), request);
    }

    @Test
    public void deleteOldRequests() {
        service.deleteOldRequests();
        verify(repository, times(1)).deleteByDateBefore(any(Long.class));
    }

    @Test
    public void confirmRequestSuccess() {
        RegistrationRequest request = TestingData.createRegistrationRequest(TestingData.createUser(false));
        when(repository.findByRegistrationUUID(any(String.class))).thenReturn(Optional.of(request));

        assertTrue(service.confirmRequest("1234", userService));
    }

    @Test
    public void confirmRequestFail()  {
        when(repository.findById("user")).thenReturn(Optional.empty());

        assertFalse(service.confirmRequest("1234", userService));
    }
}