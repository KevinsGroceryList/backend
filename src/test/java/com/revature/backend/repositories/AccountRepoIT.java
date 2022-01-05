package com.revature.backend.repositories;

import com.revature.backend.models.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AccountRepoIT {

    @Autowired
    AccountRepo accountRepo;

    @Test
    void findByUsername() {

        //arrange
        Account expectedResult = new Account(1,"kchi123", "pass123", "Kevin", "Childs");

        //act
        Account actualResult = this.accountRepo.findByUsername("kchi123");

        //assert
        assertEquals(expectedResult, actualResult);
    }
}