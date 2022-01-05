package com.revature.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.backend.models.Account;
import com.revature.backend.models.JsonResponse;
import com.revature.backend.services.AccountService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(SessionController.class)
class SessionControllerIT {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AccountService accountService;

    @MockBean
    private MockHttpSession session;

    @Test
    void loginAccountCorrectCredentials() throws Exception {
        Account credentials = new Account();
        credentials.setUsername("kchi123");
        credentials.setPassword("pass123");
        Account account = new Account(1,"kchi123", "pass123", "Kevin", "Childs");
        JsonResponse expectedResult = new JsonResponse("login successful", account);
        Mockito.when(this.accountService.validateAccountCredentials(credentials)).thenReturn(account);

        mvc.perform(MockMvcRequestBuilders.post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(credentials))
                .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expectedResult)));


        Mockito.verify(this.session, Mockito.times(1)).setAttribute("user-session", account.getId());

    }
}