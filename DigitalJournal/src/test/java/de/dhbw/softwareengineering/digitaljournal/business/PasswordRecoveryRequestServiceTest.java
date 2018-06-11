package de.dhbw.softwareengineering.digitaljournal.business;

import de.dhbw.softwareengineering.digitaljournal.TestingData;
import de.dhbw.softwareengineering.digitaljournal.domain.DeleteAccountRequest;
import de.dhbw.softwareengineering.digitaljournal.domain.PasswordRecoveryRequest;
import de.dhbw.softwareengineering.digitaljournal.persistence.PasswordRecoveryRequestRepository;
import de.dhbw.softwareengineering.digitaljournal.util.exceptions.DeleteAccountRequestException;
import de.dhbw.softwareengineering.digitaljournal.util.exceptions.RecoveryRequestNotFoundException;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PasswordRecoveryRequestServiceTest {

    private PasswordRecoveryRequestService service;
    private PasswordRecoveryRequestRepository repository;

    @Before
    public void setUp() {
        repository = mock(PasswordRecoveryRequestRepository.class);
        service    = new PasswordRecoveryRequestService(repository);
    }

    @Test
    public void create(){
        PasswordRecoveryRequest request = TestingData.createRecoveryRequest("user");
        when(repository.save(any(PasswordRecoveryRequest.class))).thenReturn(request);

        assertEquals(service.create(TestingData.createUser(true)),request);
    }

    @Test
    public void deleteAllByUsername() {
        service.deleteAllByUsername("user");
        verify(repository, times(1)).deleteAllByUsername("user");
    }

    @Test
    public void deleteOldRequests() {
        service.deleteOldRequests();
        verify(repository, times(1)).deleteByCreationDateBefore(any(Long.class));
    }

    @Test
    public void FindByUUIDSuccess() throws RecoveryRequestNotFoundException {
        PasswordRecoveryRequest request = TestingData.createRecoveryRequest("user");
        when(repository.findById(any(String.class))).thenReturn(Optional.of(request));

        assertEquals(request, service.findByUUID("1234"));
    }

    @Test(expected = RecoveryRequestNotFoundException.class)
    public void FindByUUIDFail() throws RecoveryRequestNotFoundException {
        when(repository.findById("user")).thenReturn(Optional.empty());

        service.findByUUID("1234");
    }
}