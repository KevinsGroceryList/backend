package com.revature.backend.services;

import com.revature.backend.models.Account;
import com.revature.backend.models.Item;
import com.revature.backend.repositories.AccountRepo;
import com.revature.backend.repositories.ItemRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ItemServiceTest {

    ItemService itemService;

    ItemRepo itemRepo = Mockito.mock(ItemRepo.class);
    AccountRepo accountRepo = Mockito.mock(AccountRepo.class);

    public ItemServiceTest(){
        this.itemService = new ItemService(itemRepo, accountRepo);
    }

    @Test
    void getAllItemsGivenAccountId() {
        //arrange
        Account account = new Account(1,"kchi123", "pass123", "Kevin", "Childs");
        List<Item> expectedResult = new ArrayList<>();
        Item item = new Item(1, "grapes", true, account);
        Item item2 = new Item(2, "cheese", false, account);
        expectedResult.add(item);
        expectedResult.add(item2);

        Mockito.when(this.itemRepo.findAllByAccountId(account.getId())).thenReturn(expectedResult);

        //act
        List<Item> actualResult = this.itemService.getAllItemsGivenAccountId(account.getId());

        //assert
        assertEquals(expectedResult, actualResult);
    }


    @Test
    void createItemAccountNull() {
        //arrange
        Account account = new Account(1,"kchi123", "pass123", "Kevin", "Childs");
        Item item = new Item(1, "grapes", true, account);
        Mockito.when(this.accountRepo.findById(account.getId())).thenReturn(Optional.ofNullable(null));

        //act
        Item actualResult = this.itemService.createItem(item, account.getId());

        //assert
        assertNull(actualResult);
        Mockito.verify(this.itemRepo, Mockito.times(0)).save(item);

    }

    @Test
    void createItemAccountNotNull() {
        //arrange
        Account account = new Account(1,"kchi123", "pass123", "Kevin", "Childs");
        Item item = new Item(1, "grapes", true, account);
        Mockito.when(this.accountRepo.findById(account.getId())).thenReturn(Optional.of(account));
        Mockito.when(this.itemRepo.save(item)).thenReturn(item);

        //act
        Item actualResult = this.itemService.createItem(item, account.getId());

        //assert
        assertEquals(item, actualResult);

    }

    @Test
    void markItemInCartItemNotFound() {
        //arrange
        Account account = new Account(1,"kchi123", "pass123", "Kevin", "Childs");
        Item item = new Item(1, "grapes", true, account);
        Mockito.when(itemRepo.findById(item.getId())).thenReturn(Optional.ofNullable(null));

        //act
        Item actualResult = this.itemService.markItemInCart(item.getId());

        //assert
        assertNull(actualResult);
        Mockito.verify(this.itemRepo, Mockito.times(0)).save(item);
    }

    @Test
    void markItemInCartItemFound() {
        //arrange
        Account account = new Account(1,"kchi123", "pass123", "Kevin", "Childs");
        Item item = new Item(1, "grapes", true, account);
        Item toggledItem = new Item(1, "grapes", false, account);
        Mockito.when(itemRepo.findById(item.getId())).thenReturn(Optional.of(item));
        Mockito.when(itemRepo.save(toggledItem)).thenReturn(toggledItem);

        //act
        Item actualResult = this.itemService.markItemInCart(item.getId());

        //assert
        assertEquals(toggledItem, actualResult);
    }

    @Test
    void removeItemFromListAccountNull() {
        //arrange
        Account account = new Account(1,"kchi123", "pass123", "Kevin", "Childs");
        Item item = new Item(1, "grapes", true, account);
        Mockito.when(this.accountRepo.findById(account.getId())).thenReturn(Optional.ofNullable(null));

        //act
        Boolean actualResult = this.itemService.removeItemFromList(account.getId(), item.getId());

        //assert
        assertFalse(actualResult);
    }

    @Test
    void removeItemFromListItemNull() {
        //arrange
        Account account = new Account(1,"kchi123", "pass123", "Kevin", "Childs");
        Item item = new Item(1, "grapes", true, account);
        Mockito.when(this.accountRepo.findById(account.getId())).thenReturn(Optional.of(account));
        Mockito.when(this.itemRepo.findById(item.getId())).thenReturn(Optional.ofNullable(null));

        //act
        Boolean actualResult = this.itemService.removeItemFromList(account.getId(), item.getId());

        //assert
        assertFalse(actualResult);
    }

    @Test
    void removeItemFromListItemAndAccountDontMatch() {
        //arrange
        Account account = new Account(1,"kchi123", "pass123", "Kevin", "Childs");
        Account account2 = new Account(2,"kchi1234", "pass123", "Kevin", "Childs");
        Item item = new Item(1, "grapes", true, account2);
        Mockito.when(this.accountRepo.findById(account.getId())).thenReturn(Optional.of(account));
        Mockito.when(this.itemRepo.findById(item.getId())).thenReturn(Optional.of(item));

        //act
        Boolean actualResult = this.itemService.removeItemFromList(account.getId(), item.getId());

        //assert
        assertFalse(actualResult);
    }

    @Test
    void removeItemFromListItemAndAccountMatch() {
        //arrange
        Account account = new Account(1,"kchi123", "pass123", "Kevin", "Childs");
        Item item = new Item(1, "grapes", true, account);
        Mockito.when(this.accountRepo.findById(account.getId())).thenReturn(Optional.of(account));
        Mockito.when(this.itemRepo.findById(item.getId())).thenReturn(Optional.of(item));
        //act
        Boolean actualResult = this.itemService.removeItemFromList(account.getId(), item.getId());

        //assert
        assertTrue(actualResult);
        Mockito.verify(this.itemRepo,Mockito.times(1)).deleteById(item.getId());
    }

    @Test
    void removeItemsInCart() {
        Account account = new Account(1,"kchi123", "pass123", "Kevin", "Childs");

        this.itemService.removeItemsInCart(account.getId());

        Mockito.verify(itemRepo, Mockito.times(1)).deleteByAccountIdAndInCart(account.getId(), true);

    }
}