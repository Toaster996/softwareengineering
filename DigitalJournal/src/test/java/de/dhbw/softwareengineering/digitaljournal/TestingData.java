package de.dhbw.softwareengineering.digitaljournal;

import de.dhbw.softwareengineering.digitaljournal.domain.ChangeMailRequest;
import de.dhbw.softwareengineering.digitaljournal.domain.ContactRequest;
import de.dhbw.softwareengineering.digitaljournal.domain.DeleteAccountRequest;
import de.dhbw.softwareengineering.digitaljournal.domain.Goal;
import de.dhbw.softwareengineering.digitaljournal.domain.Image;
import de.dhbw.softwareengineering.digitaljournal.domain.Journal;
import de.dhbw.softwareengineering.digitaljournal.domain.PasswordRecoveryRequest;
import de.dhbw.softwareengineering.digitaljournal.domain.RegistrationRequest;
import de.dhbw.softwareengineering.digitaljournal.domain.User;
import de.dhbw.softwareengineering.digitaljournal.domain.form.RegistrationUser;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    /*     ImageTest      */
    public static Image createImage(){
        Image img = new Image();
              img.setJournalid("1234");
              img.setImage(new byte[2]);
              img.setId("1234");
        return img;
    }

    /*     DeleteAccountTest      */
    public static DeleteAccountRequest createDeleteAccountRequest(String username){
        DeleteAccountRequest request = new DeleteAccountRequest();

        request.setUsername(username);
        request.setDate(1000L);
        request.setRequestid("12324");

        return request;
    }

    /*     ContactRequestTest      */
    public static ContactRequest createContactRequest(){
        ContactRequest contactRequest = new ContactRequest();
        contactRequest.setEmail("abc@def.gh");
        contactRequest.setId("1234");
        contactRequest.setMessage("Message");
        contactRequest.setName("Name");
        contactRequest.setSolved(false);

        return contactRequest;
    }

    /*     ChangeMailTest      */

    public static ChangeMailRequest createChangeMailRequest(String email){
        ChangeMailRequest request = new ChangeMailRequest();
        request.setUsername("user");
        request.setDate(System.currentTimeMillis());
        request.setNewmail(email);
        request.setNewmailid(UUID.randomUUID().toString());
        request.setOldmailid(UUID.randomUUID().toString());

        return request;
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

    public static RegistrationUser createEmptyRegistrationUser() {
        RegistrationUser registrationUser = new RegistrationUser();
        registrationUser.setName("");
        registrationUser.setEmail("");
        registrationUser.setPassword("");
        registrationUser.setPasswordConfirm("");
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

    public static RegistrationRequest createRegistrationRequest(User user) {
        RegistrationRequest request = new RegistrationRequest();
                            request.setUsername(user.getUsername());
                            request.setDate(System.currentTimeMillis());
                            request.setRegistrationUUID(UUID.randomUUID().toString());

        return request;
    }


    public static PasswordRecoveryRequest createRecoveryRequest(String user) {
        PasswordRecoveryRequest recoveryRequest = new PasswordRecoveryRequest();
        recoveryRequest.setUsername(user);
        recoveryRequest.setRecoveryUUID("1234");
        recoveryRequest.setCreationDate(1000L);
        return recoveryRequest;
    }
}
