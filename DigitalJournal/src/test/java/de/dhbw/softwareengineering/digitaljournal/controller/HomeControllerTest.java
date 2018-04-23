package de.dhbw.softwareengineering.digitaljournal.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static de.dhbw.softwareengineering.digitaljournal.TestingData.*;

public class HomeControllerTest {

    private MockMvc mockMvc;

    @Before
    public void setup() {
        HomeController homeController = new HomeController();

        mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
    }

    @Test
    public void home() throws Exception {
        sendRequestToController(mockMvc, HttpMethod.GET, "/", HttpStatus.OK);
    }

    @Test
    public void unauthorized() throws Exception {
        sendRequestToController(mockMvc, HttpMethod.GET, "/unauthorized", HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void loginError() throws Exception {
        sendRequestToController(mockMvc, HttpMethod.GET, "/login-error", HttpStatus.OK);
    }
}