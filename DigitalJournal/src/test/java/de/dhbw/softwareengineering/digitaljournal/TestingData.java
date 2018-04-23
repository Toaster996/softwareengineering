package de.dhbw.softwareengineering.digitaljournal;

import de.dhbw.softwareengineering.digitaljournal.domain.User;
import de.dhbw.softwareengineering.digitaljournal.domain.form.RegistrationUser;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

    /*    /UserServiceTest     */
}
