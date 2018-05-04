package de.dhbw.softwareengineering.digitaljournal.controller;

import de.dhbw.softwareengineering.digitaljournal.TestingData;
import de.dhbw.softwareengineering.digitaljournal.business.GoalService;
import de.dhbw.softwareengineering.digitaljournal.business.JournalService;
import de.dhbw.softwareengineering.digitaljournal.persistence.GoalRepository;
import de.dhbw.softwareengineering.digitaljournal.persistence.JournalRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.Principal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class JournalControllerTest {
    private MockMvc mockMvc;
    private JournalService journalService;
    private JournalRepository journalRepository;
    private GoalService goalService;
    private GoalRepository goalRepository;

    @Before
    public void setup(){
        journalRepository = mock(JournalRepository.class);
        journalService = new JournalService(journalRepository);

        goalRepository = mock(GoalRepository.class);
        goalService = new GoalService(goalRepository);

        JournalController journalController = new JournalController(journalService, goalService);
        mockMvc = MockMvcBuilders.standaloneSetup(journalController).build();
    }

    @Test
    public void root() throws Exception {
        when(journalService.findAll(any(String.class))).thenReturn(TestingData.findAllJournals(5));
        mockMvc.perform(get("/journal").principal(mock(Principal.class))).andExpect(status().isOk());

        //TestingData.sendRequestToController(mockMvc, HttpMethod.GET, "/journal", HttpStatus.OK);
    }

    @Test
    public void create() throws Exception {
        mockMvc.perform(post("/journal/create")
                .flashAttr("journal", TestingData.createJournal())
                .principal(mock(Principal.class)))
                .andExpect(status().is3xxRedirection());
    }


}
