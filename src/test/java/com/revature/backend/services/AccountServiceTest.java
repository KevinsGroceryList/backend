package com.revature.backend.services;

import com.revature.backend.models.Account;
import com.revature.backend.repositories.AccountRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {

    AccountService accountService;

    AccountRepo accountRepo = Mockito.mock(AccountRepo.class);

    public AccountServiceTest(){
        this.accountService = new AccountService(accountRepo);
    }

    //positive
    @Test
    void getAccountByIdAccountFound() {
        //arrange
        Account expectedResult = new Account(1,"kchi123", "pass123", "Kevin", "Childs");
        Mockito.when(this.accountRepo.findById(expectedResult.getId())).thenReturn(Optional.of(expectedResult));

        //act
        Account actualResult = this.accountService.getAccountById(expectedResult.getId());

        //assert
        assertEquals(expectedResult, actualResult);
    }

    //negative
    @Test
    void getAccountByIdAccountNotFound() {
        //arrange
        Account account = new Account(1,"kchi123", "pass123", "Kevin", "Childs");
        Account expectedResult = null;
        Mockito.when(this.accountRepo.findById(account.getId())).thenReturn(Optional.ofNullable(expectedResult));

        //act
        Account actualResult = this.accountService.getAccountById(account.getId());

        //assert
        assertEquals(expectedResult, actualResult);
    }


    @Test
    void getAccountByUsernameAccountFound() {
        //arrange
        Account expectedResult = new Account(1,"kchi123", "pass123", "Kevin", "Childs");
        Mockito.when(this.accountRepo.findByUsername(expectedResult.getUsername())).thenReturn(expectedResult);

        //act
        Account actualResult = this.accountService.getAccountByUsername(expectedResult.getUsername());

        //assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getAccountByUsernameAccountNotFound() {
        //arrange
        String username ="kchi123";
        Account expectedResult = null;
        Mockito.when(this.accountRepo.findByUsername(username)).thenReturn(null);

        //act
        Account actualResult = this.accountService.getAccountByUsername(username);

        //assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void createAccountSuccessful() {
        //arrange
        Account expectedResult = new Account(1,"kchi123", "pass123", "Kevin", "Childs");
        Mockito.when(this.accountRepo.findByUsername(expectedResult.getUsername())).thenReturn(null);
        Mockito.when(this.accountRepo.save(expectedResult)).thenReturn(expectedResult);

        //act
        Account actualResult = this.accountService.createAccount(expectedResult);

        //assert
        assertEquals(expectedResult,actualResult);
    }

    @Test
    void createAccountUnsuccessful() {
        //arrange
        Account expectedResult = new Account(1,"kchi123", "pass123", "Kevin", "Childs");
        Mockito.when(this.accountRepo.findByUsername(expectedResult.getUsername())).thenReturn(expectedResult);

        //act
        Account actualResult = this.accountService.createAccount(expectedResult);

        //assert
        assertNull(actualResult);

        Mockito.verify(this.accountRepo, Mockito.times(0)).save(expectedResult);
    }

    @Test
    void validateAccountCredentialsAccountFound() {
        Account expectedResult = new Account(1,"kchi123", "pass123", "Kevin", "Childs");
        Mockito.when(this.accountRepo.findByUsername(expectedResult.getUsername())).thenReturn(expectedResult);

        Account actualResult = this.accountService.validateAccountCredentials(expectedResult);

        assertEquals(expectedResult, actualResult);

    }

    @Test
    void validateAccountCredentialsPasswordIncorrect() {
        Account account = new Account(1,"kchi123", "pass123", "Kevin", "Childs");
        Account returnObj = new Account(1,"kchi123", "pass1234", "Kevin", "Childs");
        Mockito.when(this.accountRepo.findByUsername(account.getUsername())).thenReturn(returnObj);

        Account actualResult = this.accountService.validateAccountCredentials(account);

        assertNull(actualResult);

    }

    @Test
    void validateAccountCredentialsAccountNotFound() {
        Account account = new Account(1,"kchi123", "pass123", "Kevin", "Childs");
        Mockito.when(this.accountRepo.findByUsername(account.getUsername())).thenReturn(null);

        Account actualResult = this.accountService.validateAccountCredentials(account);

        assertNull(actualResult);

    }
}