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
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/*
* SpringBoot has some annotations used for testing:
*  - @SpringBootTest: loads full application context, exactly how you start a spring container when you run the spring boot application
*  - @WebMvcTest: loads application context config only for the web layer (controllers)
*       - MockMvc - allows us to perform a specific HttpRequest and validate the response is correct
*       - MockMvcRequestBuilders - allows us to define the request we want to perform
*       - MockMvcResultMatchers - get information about the http response
*  - @DataJpaTest: loads the application only for JPA and persistence layer
*
* */
@WebMvcTest(AccountController.class)
public class AccountControllerIT {

    @Autowired
    private MockMvc mvc;

    @MockBean
    AccountService accountService;

    @Test
    public void registerAccountValidateCredentials() throws Exception {
        //arrange
        Account credentials = new Account();
        credentials.setUsername("kchi123");
        credentials.setPassword("pass123");
        Account account = new Account(1,"kchi123", "pass123", "Kevin", "Childs");
        JsonResponse expectedResult = new JsonResponse("account created", account);
        Mockito.when(this.accountService.createAccount(credentials)).thenReturn(account);

        RequestBuilder request = MockMvcRequestBuilders.post("/account")
                .contentType(MediaType.APPLICATION_JSON) //changing the Content-Type to application/json
                .content(new ObjectMapper().writeValueAsString(credentials)); // request body... ObjectMapper converts object to json string and visa versa (JACKSON REMEMBER)

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())//validation of status code
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expectedResult))); //validation of the body
    }

    @Test
    public void registerAccountInvalidCredentials() throws Exception {
        //arrange
        Account credentials = new Account();
        credentials.setUsername("kchi123");
        credentials.setPassword("pass123");
        Account account = new Account(1,"kchi123", "pass123", "Kevin", "Childs");
        JsonResponse expectedResult = new JsonResponse("username is already taken", null);
        Mockito.when(this.accountService.createAccount(credentials)).thenReturn(null);

        //act
        //assert
        mvc.perform(MockMvcRequestBuilders.post("/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(credentials)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())//validation of status code
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expectedResult))); //validation of body
    }


}
