package com.revature.backend.repositories;

import com.revature.backend.models.Account;
import com.revature.backend.models.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ItemRepoIT {

    @Autowired
    ItemRepo itemRepo;

    @Test
    void findAllByAccountId() {
        //arrange
        Account account = new Account(1,"kchi123", "pass123", "Kevin", "Childs");
        List<Item> expectedResult = new ArrayList<>();
        expectedResult.add(new Item(1, "grapes", false, account));
        expectedResult.add(new Item(2, "cheese", true, account));


        //act
        List<Item> actualResult = this.itemRepo.findAllByAccountId(account.getId());

        //assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void deleteByAccountIdAndInCart() {
        //arrange
        Account account = new Account(1,"kchi123", "pass123", "Kevin", "Childs");
        List<Item> expectedResult = new ArrayList<>();
        expectedResult.add(new Item(1, "grapes", false, account));


        //act
        this.itemRepo.deleteByAccountIdAndInCart(account.getId(), true);
        List<Item> actualResult = this.itemRepo.findAllByAccountId(account.getId());

        //assert
        assertEquals(expectedResult, actualResult);
    }
}