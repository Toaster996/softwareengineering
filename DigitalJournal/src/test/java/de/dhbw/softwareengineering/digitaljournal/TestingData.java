package de.dhbw.softwareengineering.digitaljournal;

import de.dhbw.softwareengineering.digitaljournal.domain.Goal;
import de.dhbw.softwareengineering.digitaljournal.domain.Journal;
import de.dhbw.softwareengineering.digitaljournal.domain.User;
import de.dhbw.softwareengineering.digitaljournal.domain.form.RegistrationUser;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestingData {

    public static void sendRequestToController(MockMvc mockMvc, HttpMethod type, String url, HttpStatus status) throws Exception {
        mockMvc.perform((getRequestType(type, url))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(status.value()));
    }

    private static MockHttpServletRequestBuilder getRequestType(HttpMethod type, String url) {
        switch (type) {
            case DELETE:
                return delete(url);
            case PUT:
                return put(url);
            case POST:
                return post(url);
            case GET:
                return get(url);
            default:
                return get(url);
        }
    }

    /*     UserServiceTest     */
    public static User createUser(boolean verified) {
        User user = new User();
        user.setUsername("user");
        user.setEmail("user@digitaljournal.com");
        user.setRegistrationDate(System.currentTimeMillis());
        user.setPassword("$2a$10$j/7dP2Ed2PycrXLQyEi89.y/Y8zRdyAfui3TdUlsjH0Di2WA5VgS6");
        user.setVerified(verified);
        return user;
    }

    public static RegistrationUser createRegistrationUser() {
        RegistrationUser registrationUser = new RegistrationUser();
        registrationUser.setName("user");
        registrationUser.setEmail("user@digitaljournal.com");
        registrationUser.setPassword("password");
        registrationUser.setPasswordConfirm("password");
        return registrationUser;
    }

    /*    /JournalServiceTest     */
    public static Journal createJournal(){
        Journal journal = new Journal();
        journal.setJournalid("42");
        journal.setDate(System.currentTimeMillis());
        journal.setUsername("user");
        journal.setContent("Lorem Ipsum");
        journal.setJournalName("Test Journal");
        return journal;
    }

    public static List<Journal> findAllJournals(int quantity){
        List<Journal> journals = new ArrayList<>();
        for(int i = 0; i < quantity; i++){
            journals.add(mock(Journal.class));
        }
        return journals;
    }

    public static Goal createGoal(){
        Goal goal = new Goal();
        goal.setId("13");
        goal.setDate(356);
        goal.setDescription("Nice description");
        goal.setName("Motivating Name");
        goal.setUsername("user");
        return goal;
    }
}
