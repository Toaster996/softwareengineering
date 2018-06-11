package de.dhbw.softwareengineering.digitaljournal.business;

import de.dhbw.softwareengineering.digitaljournal.TestingData;
import de.dhbw.softwareengineering.digitaljournal.domain.ContactRequest;
import de.dhbw.softwareengineering.digitaljournal.persistence.ContactRequestRepository;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ContactRequestServiceTest {

    private ContactRequestService service;
    private ContactRequestRepository repository;

    @Before
    public void setUp() {
        repository  = mock(ContactRequestRepository.class);
        service     = new ContactRequestService(repository);
    }

    @Test
    public void create(){
        ContactRequest request = TestingData.createContactRequest();

        service.create(request);
        verify(repository, times(1)).save(request);
    }

    @Test
    public void findAllUnsolvedRequests(){
        service.findAllUnsolvedRequests();
        verify(repository, times(1)).findAllBySolvedFalse();
    }

    @Test
    public void solve(){
        ContactRequest request = TestingData.createContactRequest();
        service.solve(request);

        verify(repository, times(1)).save(request);
    }
}