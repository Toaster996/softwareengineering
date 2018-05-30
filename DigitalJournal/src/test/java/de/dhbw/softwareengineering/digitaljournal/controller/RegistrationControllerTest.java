package de.dhbw.softwareengineering.digitaljournal.controller;

import de.dhbw.softwareengineering.digitaljournal.TestingData;
import de.dhbw.softwareengineering.digitaljournal.business.EmailService;
import de.dhbw.softwareengineering.digitaljournal.business.RegistrationRequestService;
import de.dhbw.softwareengineering.digitaljournal.business.UserService;
import de.dhbw.softwareengineering.digitaljournal.domain.RegistrationRequest;
import de.dhbw.softwareengineering.digitaljournal.domain.User;
import de.dhbw.softwareengineering.digitaljournal.domain.form.RegistrationUser;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.UUID;

import static de.dhbw.softwareengineering.digitaljournal.util.Constants.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RegistrationControllerTest {

    private MockMvc mockMvc;

    private UserService userService;

    private EmailService emailService;

    private RegistrationRequestService registrationRequestService;

    @Before
    public void setup(){
        userService = mock(UserService.class);
        emailService = mock(EmailService.class);
        registrationRequestService = mock(RegistrationRequestService.class);

        RegistrationController registrationController = new RegistrationController(userService, emailService, registrationRequestService);
        mockMvc = MockMvcBuilders.standaloneSetup(registrationController).build();
    }

    @Test
    public void registerUserSuccessful() throws Exception {
        RegistrationUser registrationUser = TestingData.createRegistrationUser();
        User user = TestingData.createUser(false);
        RegistrationRequest request = TestingData.createRegistrationRequest(user);

        when(userService.create(registrationUser)).thenReturn(user);
        when(registrationRequestService.create(registrationUser.getName())).thenReturn(request);

        mockMvc.perform(post("/register")
                .flashAttr("registrationUser", registrationUser))
                .andExpect(status().isOk());

        verify(userService, times(1)).create(registrationUser);
        verify(registrationRequestService, times(1)).create(registrationUser.getName());
        verify(emailService, times(1)).sendRegistrationMail(any(User.class), any(RegistrationRequest.class));
    }

    @Test
    public void registerUserFailedEmptyForm() throws Exception {
        RegistrationUser registrationUser = TestingData.createEmptyRegistrationUser();

        mockMvc.perform(post("/register")
                .flashAttr("registrationUser", registrationUser))
                .andDo(mvcResult -> {
                    ModelAndView modelAndView = mvcResult.getModelAndView();
                    Map<String, Object> model =  modelAndView.getModel();

                    assertEquals(STATUSCODE_EMPTYFORM, model.get(STATUS_ATTRIBUTE_NAME));
                })
                .andExpect(status().isOk());
    }

    @Test
    public void registerUserFailedPasswordMismatch() throws Exception {
        RegistrationUser registrationUser = TestingData.createRegistrationUser();
                         registrationUser.setPasswordConfirm("Bernd Braun braut Bier.");

        mockMvc.perform(post("/register")
                .flashAttr("registrationUser", registrationUser))
                .andDo(mvcResult -> {
                    ModelAndView modelAndView = mvcResult.getModelAndView();
                    Map<String, Object> model =  modelAndView.getModel();

                    assertEquals(STATUSCODE_PWMISSMATCH, model.get(STATUS_ATTRIBUTE_NAME));
                })
                .andExpect(status().isOk());
    }

    @Test
    public void registerUserFailedUsernameTooLong() throws Exception {
        RegistrationUser registrationUser = TestingData.createRegistrationUser();
                         registrationUser.setName("VERYLONGSTRINGTHATWILLNEVERENDEVENAFTERTHETIMEHASENDED!");

        mockMvc.perform(post("/register")
                .flashAttr("registrationUser", registrationUser))
                .andDo(mvcResult -> {
                    ModelAndView modelAndView = mvcResult.getModelAndView();
                    Map<String, Object> model =  modelAndView.getModel();

                    assertEquals(STATUSCODE_USERNAMETOOLONG, model.get(STATUS_ATTRIBUTE_NAME));
                })
                .andExpect(status().isOk());
    }

    @Test
    public void registerUserFailedUsernameNotAlphanumeric() throws Exception {
        RegistrationUser registrationUser = TestingData.createRegistrationUser();
        registrationUser.setName("a0!_.asd");

        mockMvc.perform(post("/register")
                .flashAttr("registrationUser", registrationUser))
                .andDo(mvcResult -> {
                    ModelAndView modelAndView = mvcResult.getModelAndView();
                    Map<String, Object> model =  modelAndView.getModel();

                    assertEquals(STATUSCODE_ALPHANUMERIC, model.get(STATUS_ATTRIBUTE_NAME));
                })
                .andExpect(status().isOk());
    }

    @Test
    public void registerUserFailedEmailTooLong() throws Exception {
        RegistrationUser registrationUser = TestingData.createRegistrationUser();
        registrationUser.setEmail("anicemail.makememuchlongersoiamlongerthan100charactersinordertoerrorgoddamn.fritzelbergerhausenbeiner@web.de");

        mockMvc.perform(post("/register")
                .flashAttr("registrationUser", registrationUser))
                .andDo(mvcResult -> {
                    ModelAndView modelAndView = mvcResult.getModelAndView();
                    Map<String, Object> model =  modelAndView.getModel();

                    assertEquals(STATUSCODE_EMAILTOOLONG, model.get(STATUS_ATTRIBUTE_NAME));
                })
                .andExpect(status().isOk());
    }

    @Test
    public void registerUserFailedEmailInUse() throws Exception {
        RegistrationUser registrationUser = TestingData.createRegistrationUser();

        when(userService.existByEmail(registrationUser.getEmail())).thenReturn(true);

        mockMvc.perform(post("/register")
                .flashAttr("registrationUser", registrationUser))
                .andDo(mvcResult -> {
                    ModelAndView modelAndView = mvcResult.getModelAndView();
                    Map<String, Object> model =  modelAndView.getModel();

                    assertEquals(STATUSCODE_EMAILALREADYINUSE, model.get(STATUS_ATTRIBUTE_NAME));
                })
                .andExpect(status().isOk());
    }

    @Test
    public void registerUserFailedUsernameInUse() throws Exception {
        RegistrationUser registrationUser = TestingData.createRegistrationUser();

        when(userService.existByUsername(registrationUser.getName())).thenReturn(true);

        mockMvc.perform(post("/register")
                .flashAttr("registrationUser", registrationUser))
                .andDo(mvcResult -> {
                    ModelAndView modelAndView = mvcResult.getModelAndView();
                    Map<String, Object> model =  modelAndView.getModel();

                    assertEquals(STATUSCODE_USERNAMEALREADYINUSE, model.get(STATUS_ATTRIBUTE_NAME));
                })
                .andExpect(status().isOk());
    }

    @Test
    public void registerUserFailedPasswordTooShort() throws Exception {
        RegistrationUser registrationUser = TestingData.createRegistrationUser();
        registrationUser.setPassword("a");
        registrationUser.setPasswordConfirm("a");

        mockMvc.perform(post("/register")
                .flashAttr("registrationUser", registrationUser))
                .andDo(mvcResult -> {
                    ModelAndView modelAndView = mvcResult.getModelAndView();
                    Map<String, Object> model =  modelAndView.getModel();

                    assertEquals(STATUSCODE_PWTOOSHORT, model.get(STATUS_ATTRIBUTE_NAME));
                })
                .andExpect(status().isOk());
    }

    @Test
    public void registerUserFailedPasswordTooLong() throws Exception {
        RegistrationUser registrationUser = TestingData.createRegistrationUser();
        registrationUser.setPassword("VERYLONGSTRINGTHATWILLNEVERENDEVENAFTERTHETIMEHASENDED!");
        registrationUser.setPasswordConfirm("VERYLONGSTRINGTHATWILLNEVERENDEVENAFTERTHETIMEHASENDED!");

        mockMvc.perform(post("/register")
                .flashAttr("registrationUser", registrationUser))
                .andDo(mvcResult -> {
                    ModelAndView modelAndView = mvcResult.getModelAndView();
                    Map<String, Object> model =  modelAndView.getModel();

                    assertEquals(STATUSCODE_PWTOOLONG, model.get(STATUS_ATTRIBUTE_NAME));
                })
                .andExpect(status().isOk());
    }

    @Test
    public void registerUserFailedEmailInvalid() throws Exception {
        RegistrationUser registrationUser = TestingData.createRegistrationUser();
        registrationUser.setEmail("mail.de");

        mockMvc.perform(post("/register")
                .flashAttr("registrationUser", registrationUser))
                .andDo(mvcResult -> {
                    ModelAndView modelAndView = mvcResult.getModelAndView();
                    Map<String, Object> model =  modelAndView.getModel();

                    assertEquals(STATUSCODE_EMAILINVALID, model.get(STATUS_ATTRIBUTE_NAME));
                })
                .andExpect(status().isOk());
    }


    @Test
    public void confirmEmailSuccessful() throws Exception {
        String confirmUUID = UUID.randomUUID().toString();

        when(registrationRequestService.confirmRequest(confirmUUID, userService)).thenReturn(true);

        mockMvc.perform(get("/confirmemail/{uuid}", confirmUUID))
                .andDo(mvcResult -> {
                    ModelAndView modelAndView = mvcResult.getModelAndView();
                    Map<String, Object> model =  modelAndView.getModel();

                    assertEquals("true", model.get(STATUS_RESPONSE_ATTRIBUTE_NAME));
                })
                .andExpect(status().isOk());
    }

    @Test
    public void confirmEmailFailed() throws Exception {
        String confirmUUID = UUID.randomUUID().toString();

        when(registrationRequestService.confirmRequest(confirmUUID, userService)).thenReturn(false);

        mockMvc.perform(get("/confirmemail/{uuid}", confirmUUID))
                .andDo(mvcResult -> {
                    ModelAndView modelAndView = mvcResult.getModelAndView();
                    Map<String, Object> model =  modelAndView.getModel();

                    assertEquals("false", model.get(STATUS_RESPONSE_ATTRIBUTE_NAME));
                })
                .andExpect(status().isOk());
    }
}
