package de.dhbw.softwareengineering.digitaljournal.business;

import de.dhbw.softwareengineering.digitaljournal.TestingData;
import de.dhbw.softwareengineering.digitaljournal.domain.ChangeMailRequest;
import de.dhbw.softwareengineering.digitaljournal.domain.RegistrationRequest;
import de.dhbw.softwareengineering.digitaljournal.domain.User;
import de.dhbw.softwareengineering.digitaljournal.persistence.ChangeMailRequestRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ChangeMailRequestServiceTest {

    private ChangeMailRequestService service;
    private ChangeMailRequestRepository repository;

    private final String newMail = "mail@abc.de";

    @Before
    public void setUp() {
        repository = mock(ChangeMailRequestRepository.class);
        service    = new ChangeMailRequestService(repository);
    }

    @Test
    public void createNewRequestNoUserWithNewMail() {
        ChangeMailRequest request = TestingData.createChangeMailRequest(newMail);
        when(repository.existsById(request.getUsername())).thenReturn(false);
        when(repository.save(any(ChangeMailRequest.class))).thenReturn(request);


        assertEquals(service.create(request.getUsername(), newMail), request);
    }

    @Test
    public void createNewRequestUserWithNewMailExists() {
        ChangeMailRequest request = TestingData.createChangeMailRequest(newMail);
        when(repository.existsById(request.getUsername())).thenReturn(true);

        assertNull(service.create(request.getUsername(), newMail));
    }

    @Test
    public void confirmOldMailSuccess() {
        ChangeMailRequest request = TestingData.createChangeMailRequest(newMail);
        when(repository.findByOldmailid(any(String.class))).thenReturn(Optional.of(request));

        assertEquals(request.getUsername(), service.confirm("123456"));
    }

    @Test
    public void confirmOldMailFail() {
        when(repository.findByOldmailid(any(String.class))).thenReturn(Optional.empty());

        assertNull(service.confirm("123456"));
    }

    @Test
    public void confirmNewMailSuccess() {
        ChangeMailRequest request = TestingData.createChangeMailRequest(newMail);
        when(repository.findByNewmailid(any(String.class))).thenReturn(Optional.of(request));

        assertEquals(request.getUsername(), service.confirm("123456"));
    }

    @Test
    public void confirmNewMailFail() {
        when(repository.findByNewmailid(any(String.class))).thenReturn(Optional.empty());

        assertNull(service.confirm("123456"));
    }

    @Test
    public void isConfirmedSuccess() {
        ChangeMailRequest request = TestingData.createChangeMailRequest(newMail);
        when(repository.findById(any(String.class))).thenReturn(Optional.of(request));

        boolean[] result = service.isConfirmed("123456");

        assertEquals(result[0] && result[1], request.isOldconfirmed() &&  request.isNewconfirmed());
    }

    @Test
    public void isConfirmedFail() {
        when(repository.findById(any(String.class))).thenReturn(Optional.empty());

        boolean[] result = service.isConfirmed("123456");

        assertFalse(result[0] && result[1]);
    }

    @Test
    public void deleteOldRequests() {
        service.deleteOldRequests();

        verify(repository, times(1)).deleteByDateBefore(any(Long.class));
    }

    @Test
    public void delete() {
        service.delete("user");

        verify(repository, times(1)).deleteById("user");
    }

    @Test
    public void getNewMailSuccess() {
        ChangeMailRequest request = TestingData.createChangeMailRequest(newMail);
        when(repository.findById(any(String.class))).thenReturn(Optional.of(request));

        assertEquals(service.getNewMail("user"),request.getNewmail());
    }

    @Test
    public void getNewMailFail() {
        when(repository.findById(any(String.class))).thenReturn(Optional.empty());

        assertNull(service.getNewMail("user"));
    }

    @Test
    public void hasRequest() {
        when(repository.existsById(any(String.class))).thenReturn(true);

        assertTrue(service.hasRequest("user"));
    }

}