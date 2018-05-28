package de.dhbw.softwareengineering.digitaljournal.business;

import de.dhbw.softwareengineering.digitaljournal.TestingData;
import de.dhbw.softwareengineering.digitaljournal.domain.Journal;
import de.dhbw.softwareengineering.digitaljournal.persistence.JournalRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


public class JournalServiceTest {
    private JournalService journalService;
    private JournalRepository journalRepository;
    private Journal journal;

    @Before
    public void setup(){
        journalRepository = mock(JournalRepository.class);
        journalService = new JournalService(journalRepository);

    }

    @Test
    public void createJournal(){
        journal = TestingData.createJournal();
        when(journalService.save(journal)).thenReturn(journal);
        assertEquals(journalService.save(journal), journal);
    }

    @Test
    public void updateTest(){
        final String NEW_JOURNAL_NAME = "changed";
        Journal updtedJournal = new Journal("21", NEW_JOURNAL_NAME, "dawadas", "user", System.currentTimeMillis());
        when(journalService.update(updtedJournal)).thenReturn(journal);
        assertEquals(journalService.update(updtedJournal), journal);
    }

}
