package com.revature.backend.controllers;

import com.revature.backend.models.Account;
import com.revature.backend.models.JsonResponse;
import com.revature.backend.services.AccountService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class AccountControllerTest {

    AccountService accountService = Mockito.mock(AccountService.class);

    AccountController accountController;

    public AccountControllerTest(){
        this.accountController = new AccountController(accountService);
    }

    @Test
    void registerAccountValidCredentials() {
        //arrange
        //sonar cloud test
        Account credentials = new Account();
        credentials.setUsername("kchi123");
        credentials.setPassword("pass123");
        Account account = new Account(1,"kchi123", "pass123", "Kevin", "Childs");
        ResponseEntity<JsonResponse> expectedResult = ResponseEntity.ok(new JsonResponse("account created", account));
        Mockito.when(this.accountService.createAccount(credentials)).thenReturn(account);

        //act
        ResponseEntity<JsonResponse> actualResult = this.accountController.registerAccount(credentials);

        //assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void registerAccountValidInvalidCredentials() {
        //arrange
        Account credentials = new Account();
        credentials.setUsername("kchi123");
        credentials.setPassword("pass123");
        Account account = null;
        ResponseEntity<JsonResponse> expectedResult = ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new JsonResponse("username is already taken", null));

        Mockito.when(this.accountService.createAccount(credentials)).thenReturn(account);

        //act
        ResponseEntity<JsonResponse> actualResult = this.accountController.registerAccount(credentials);

        //assert
        assertEquals(expectedResult, actualResult);
    }
}