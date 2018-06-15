package de.dhbw.softwareengineering.digitaljournal.business;

import de.dhbw.softwareengineering.digitaljournal.TestingData;
import de.dhbw.softwareengineering.digitaljournal.domain.DeleteAccountRequest;
import de.dhbw.softwareengineering.digitaljournal.persistence.DeleteAccountRequestRepository;
import de.dhbw.softwareengineering.digitaljournal.util.exceptions.DeleteAccountRequestException;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DeleteAccountlRequestServiceTest {

    private DeleteAccountRequestService service;
    private DeleteAccountRequestRepository repository;

    @Before
    public void setUp() {
        repository = mock(DeleteAccountRequestRepository.class);
        service    = new DeleteAccountRequestService(repository);
    }

    @Test
    public void create(){
        DeleteAccountRequest request = TestingData.createDeleteAccountRequest("user");
        when(repository.save(any(DeleteAccountRequest.class))).thenReturn(request);

        assertEquals(service.create("user"),request);
    }

    @Test
    public void FindByUUIDSuccess() throws DeleteAccountRequestException {
        DeleteAccountRequest request = TestingData.createDeleteAccountRequest("user");
        when(repository.findById(any(String.class))).thenReturn(Optional.of(request));

        assertEquals(request, service.findByUUID("1234"));
    }

    @Test(expected = DeleteAccountRequestException.class)
    public void FindByUUIDFail() throws DeleteAccountRequestException {
        when(repository.findById("user")).thenReturn(Optional.empty());

        service.findByUUID("1234");
    }

    @Test
    public void deleteOldRequests() {
        service.deleteOldRequests();
        verify(repository, times(1)).deleteByDateBefore(any(Long.class));
    }

    @Test
    public void delete() {
        DeleteAccountRequest request = TestingData.createDeleteAccountRequest("user");
        service.deleteRequest(request);

        verify(repository, times(1)).delete(request);
    }

    @Test
    public void hasRequest() {
        when(repository.existsByUsername(any(String.class))).thenReturn(true);

        assertTrue(service.hasDeletionRequest("user"));
    }
}
