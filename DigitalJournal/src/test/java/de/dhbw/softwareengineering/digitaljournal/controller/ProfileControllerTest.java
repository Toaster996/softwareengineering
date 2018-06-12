package de.dhbw.softwareengineering.digitaljournal.controller;

import de.dhbw.softwareengineering.digitaljournal.TestingData;
import de.dhbw.softwareengineering.digitaljournal.business.ChangeMailRequestService;
import de.dhbw.softwareengineering.digitaljournal.business.DeleteAccountRequestService;
import de.dhbw.softwareengineering.digitaljournal.business.EmailService;
import de.dhbw.softwareengineering.digitaljournal.business.GoalService;
import de.dhbw.softwareengineering.digitaljournal.business.JournalService;
import de.dhbw.softwareengineering.digitaljournal.business.UserService;
import de.dhbw.softwareengineering.digitaljournal.util.exceptions.DeleteAccountRequestException;
import de.dhbw.softwareengineering.digitaljournal.util.exceptions.UserNotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Map;

import static de.dhbw.softwareengineering.digitaljournal.util.Constants.STATUSCODE_EMAILALREADYINUSE;
import static de.dhbw.softwareengineering.digitaljournal.util.Constants.STATUSCODE_EMAILINVALID;
import static de.dhbw.softwareengineering.digitaljournal.util.Constants.STATUSCODE_EMAILTOOLONG;
import static de.dhbw.softwareengineering.digitaljournal.util.Constants.STATUSCODE_INVALID_CREDENTIALS;
import static de.dhbw.softwareengineering.digitaljournal.util.Constants.STATUSCODE_PWMISSMATCH;
import static de.dhbw.softwareengineering.digitaljournal.util.Constants.STATUSCODE_PWTOOLONG;
import static de.dhbw.softwareengineering.digitaljournal.util.Constants.STATUSCODE_PWTOOSHORT;
import static de.dhbw.softwareengineering.digitaljournal.util.Constants.STATUSCODE_REQUEST_FAILED;
import static de.dhbw.softwareengineering.digitaljournal.util.Constants.STATUSCODE_SUCCESS;
import static de.dhbw.softwareengineering.digitaljournal.util.Constants.STATUS_ATTRIBUTE_NAME;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProfileControllerTest {

    private MockMvc mockMvc;
    private JournalService journalService;
    private UserService userService;
    private GoalService goalService;
    private EmailService emailService;
    private DeleteAccountRequestService deleteAccountRequestService;
    private ChangeMailRequestService changeMailRequestService;
    private BCryptPasswordEncoder passwordEncoder;
    private final String newMail = "test@mail.de";

    @Before
    public void setup() {
        journalService                  = mock(JournalService.class);
        userService                     = mock(UserService.class);
        goalService                     = mock(GoalService.class);
        emailService                    = mock(EmailService.class);
        deleteAccountRequestService     = mock(DeleteAccountRequestService.class);
        changeMailRequestService        = mock(ChangeMailRequestService.class);
        passwordEncoder                 = mock(BCryptPasswordEncoder.class);

        when(changeMailRequestService.hasRequest(any(String.class))).thenReturn(false);
        when(deleteAccountRequestService.hasDeletionRequest(any(String.class))).thenReturn(false);
        when(journalService.countByUsername(any(String.class))).thenReturn(3);
        when(goalService.getActiveGoals(any(String.class))).thenReturn(111);

        ProfileController profileController = new ProfileController(userService, journalService, goalService, emailService, deleteAccountRequestService, changeMailRequestService, passwordEncoder);

        mockMvc         = MockMvcBuilders.standaloneSetup(profileController).build();
    }

    @Test
    public void showProfileSuccess() throws Exception {
        when(userService.findByName(any())).thenReturn(TestingData.createUser(true));
        mockMvc.perform(get("/profile/").principal(mock(Principal.class))).andExpect(status().isOk());
    }

    @Test
    public void showProfileFail() throws Exception {
        when(userService.findByName(any())).thenThrow(UserNotFoundException.class);
        mockMvc.perform(get("/profile/").principal(mock(Principal.class))).andExpect(status().isOk());
    }

    @Test
    public void changePasswordUnknownUser() throws Exception {
        when(userService.findByName(any())).thenThrow(UserNotFoundException.class);
        mockMvc.perform(post("/profile/changepassword/")
                .principal(mock(Principal.class))
                .param("old_password","1234")
                .param("password","123456")
                .param("password_confirm","123456")
        ).andExpect(status().isOk());
    }

    @Test
    public void changePasswordInvalidPassword() throws Exception {
        when(userService.findByName(any())).thenReturn(TestingData.createUser(true));
        when(passwordEncoder.matches(any(), any())).thenReturn(false);
        mockMvc.perform(post("/profile/changepassword/")
                .principal(mock(Principal.class))
                .param("old_password","1234")
                .param("password","123456")
                .param("password_confirm","123456")
        ).andDo(result -> checkStatusAndModel(result, HttpStatus.OK, STATUS_ATTRIBUTE_NAME, STATUSCODE_INVALID_CREDENTIALS));
    }

    @Test
    public void changePasswordNotMatchingPasswords() throws Exception {
        when(userService.findByName(any())).thenReturn(TestingData.createUser(true));
        when(passwordEncoder.matches(any(), any())).thenReturn(true);
        mockMvc.perform(post("/profile/changepassword/")
                .principal(mock(Principal.class))
                .param("old_password","1234")
                .param("password","123456")
                .param("password_confirm","12345")
        ).andDo(result -> checkStatusAndModel(result, HttpStatus.OK, STATUS_ATTRIBUTE_NAME, STATUSCODE_PWMISSMATCH));
    }

    @Test
    public void changePasswordToShort() throws Exception {
        when(userService.findByName(any())).thenReturn(TestingData.createUser(true));
        when(passwordEncoder.matches(any(), any())).thenReturn(true);
        mockMvc.perform(post("/profile/changepassword/")
                .principal(mock(Principal.class))
                .param("old_password","1234")
                .param("password","123")
                .param("password_confirm","123")
        ).andDo(result -> checkStatusAndModel(result, HttpStatus.OK, STATUS_ATTRIBUTE_NAME, STATUSCODE_PWTOOSHORT));
    }

    @Test
    public void changePasswordToLong() throws Exception {
        when(userService.findByName(any())).thenReturn(TestingData.createUser(true));
        when(passwordEncoder.matches(any(), any())).thenReturn(true);
        mockMvc.perform(post("/profile/changepassword/")
                .principal(mock(Principal.class))
                .param("old_password","1234")
                .param("password","1234567891011121314151617181920212223242526272829303132333435")
                .param("password_confirm","1234567891011121314151617181920212223242526272829303132333435")
        ).andDo(result -> checkStatusAndModel(result, HttpStatus.OK, STATUS_ATTRIBUTE_NAME, STATUSCODE_PWTOOLONG));
    }

    @Test
    public void changePasswordSuccess() throws Exception {
        when(userService.findByName(any())).thenReturn(TestingData.createUser(true));
        when(passwordEncoder.matches(any(), any())).thenReturn(true);
        mockMvc.perform(post("/profile/changepassword/")
                .principal(mock(Principal.class))
                .param("old_password","1234")
                .param("password","123456")
                .param("password_confirm","123456")
        ).andDo(result -> checkStatusAndModel(result, HttpStatus.OK, STATUS_ATTRIBUTE_NAME, STATUSCODE_SUCCESS));
    }

    @Test
    public void deleteAccountUnknownUser() throws Exception {
        when(userService.findByName(any())).thenThrow(UserNotFoundException.class);
        mockMvc.perform(post("/profile/deleteaccount/")
                .principal(mock(Principal.class))
        ).andDo(result -> checkStatusAndModel(result, HttpStatus.OK, STATUS_ATTRIBUTE_NAME, STATUSCODE_REQUEST_FAILED));
    }

    @Test
    public void deleteAccountSuccess() throws Exception {
        when(userService.findByName(any())).thenReturn(TestingData.createUser(true));
        mockMvc.perform(post("/profile/deleteaccount/")
                .principal(mock(Principal.class))
        ).andDo(result -> checkStatusAndModel(result, HttpStatus.OK, STATUS_ATTRIBUTE_NAME, STATUSCODE_SUCCESS));
    }

    @Test
    public void changeMailToLong() throws Exception {
        when(userService.findByName(any())).thenReturn(TestingData.createUser(true));
        mockMvc.perform(post("/profile/mail/change/")
                .principal(mock(Principal.class))
                .param("new_mail","abcdefghijklmnopqrstuvxyzabcdefghijklmnopqrstuvxyzabcdefghijklmnopqrstuvxyzabcdefghijklmnopqrstuvxyz@abcdefghijklmnopqrstuvxyz.de")
        ).andDo(result -> checkStatusAndModel(result, HttpStatus.OK, STATUS_ATTRIBUTE_NAME, STATUSCODE_EMAILTOOLONG));
    }

    @Test
    public void changeMailInvalidMail() throws Exception {
        when(userService.findByName(any())).thenReturn(TestingData.createUser(true));
        mockMvc.perform(post("/profile/mail/change/")
                .principal(mock(Principal.class))
                .param("new_mail","bortzlamd.")
        ).andDo(result -> checkStatusAndModel(result, HttpStatus.OK, STATUS_ATTRIBUTE_NAME, STATUSCODE_EMAILINVALID));
    }

    @Test
    public void changeMailAddressAlreadyInUse() throws Exception {
        when(userService.existByEmail(any())).thenReturn(true);
        when(userService.findByName(any())).thenReturn(TestingData.createUser(true));
        mockMvc.perform(post("/profile/mail/change/")
                .principal(mock(Principal.class))
                .param("new_mail",newMail)
        ).andDo(result -> checkStatusAndModel(result, HttpStatus.OK, STATUS_ATTRIBUTE_NAME, STATUSCODE_EMAILALREADYINUSE));
    }

    @Test
    public void changeMailUserNotFound() throws Exception {
        when(userService.existByEmail(any())).thenReturn(false);
        when(userService.findByName(any())).thenThrow(UserNotFoundException.class);
        mockMvc.perform(post("/profile/mail/change/")
                .principal(mock(Principal.class))
                .param("new_mail",newMail)
        ).andDo(result -> checkStatusAndModel(result, HttpStatus.FOUND, STATUS_ATTRIBUTE_NAME, STATUSCODE_REQUEST_FAILED));
    }

    @Test
    public void changeMailSuccess() throws Exception {
        when(userService.existByEmail(any())).thenReturn(false);
        when(userService.findByName(any())).thenReturn(TestingData.createUser(true));
        when(changeMailRequestService.create("user",newMail)).thenReturn(TestingData.createChangeMailRequest(newMail));
        mockMvc.perform(post("/profile/mail/change/")
                .principal(mock(Principal.class))
                .param("new_mail",newMail)
        ).andDo(result -> checkStatusAndModel(result, HttpStatus.OK, STATUS_ATTRIBUTE_NAME, STATUSCODE_SUCCESS));
    }

    @Test
    public void changeMailNoRequestFound() throws Exception {
        when(changeMailRequestService.confirm(any())).thenReturn(null);
        mockMvc.perform(get("/profile/mail/confirm/1234")
                .principal(mock(Principal.class))
        ).andDo(result -> checkStatus(result, HttpStatus.FOUND));
    }

    @Test
    public void changeMailNoUserFound() throws Exception {
        when(changeMailRequestService.confirm(any())).thenReturn("user");
        when(changeMailRequestService.isConfirmed(any())).thenReturn(new boolean[]{true, true});
        when(changeMailRequestService.getNewMail(any())).thenReturn(newMail);
        when(userService.findByName(any())).thenThrow(UserNotFoundException.class);

        mockMvc.perform(get("/profile/mail/confirm/1234")
                .principal(mock(Principal.class))
        ).andDo(result -> checkStatus(result, HttpStatus.OK));
    }

    @Test
    public void changeMailConfirmed() throws Exception {
        when(changeMailRequestService.confirm(any())).thenReturn("user");
        when(changeMailRequestService.isConfirmed(any())).thenReturn(new boolean[]{true, true});
        when(changeMailRequestService.getNewMail(any())).thenReturn(newMail);
        when(userService.findByName(any())).thenReturn(TestingData.createUser(true));
        mockMvc.perform(get("/profile/mail/confirm/1234")
                .principal(mock(Principal.class))
        ).andDo(result -> checkStatus(result, HttpStatus.OK));
    }

    @Test
    public void deleteAccountFinallyNoRequestFound() throws Exception {
        when(deleteAccountRequestService.findByUUID(any())).thenThrow(DeleteAccountRequestException.class);
        mockMvc.perform(get("/profile/delete/1234")
                .principal(mock(Principal.class))
        ).andDo(result -> checkStatus(result, HttpStatus.FOUND));
    }

    @Test
    public void deleteAccountFinallySuccess() throws Exception {
        when(deleteAccountRequestService.findByUUID(any())).thenReturn(TestingData.createDeleteAccountRequest("user"));
        mockMvc.perform(get("/profile/delete/1234")
                .principal(mock(Principal.class))
        ).andDo(result -> checkStatus(result, HttpStatus.FOUND));
    }

    private void checkStatusAndModel(MvcResult result, HttpStatus status, String modelName, String modelValue) {
        checkStatus(result, status);

        ModelAndView modelAndView = result.getModelAndView();
        Map<String, Object> models = modelAndView.getModel();
        String value = (String) models.get(modelName);
        Assert.assertEquals(modelValue, value);
    }

    private void checkStatus(MvcResult result, HttpStatus status) {
        int statuscode = result.getResponse().getStatus();
        Assert.assertEquals(status.toString(), Integer.toString(statuscode));
    }
}
