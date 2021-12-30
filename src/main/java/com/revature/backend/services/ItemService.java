package com.revature.backend.services;

import com.revature.backend.models.Account;
import com.revature.backend.models.Item;
import com.revature.backend.repositories.AccountRepo;
import com.revature.backend.repositories.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ItemService {

    private ItemRepo itemRepo;
    private AccountRepo accountRepo;

    @Autowired
    public ItemService(ItemRepo itemRepo, AccountRepo accountRepo){
        this.itemRepo = itemRepo;
        this.accountRepo = accountRepo;
    }

    public List<Item> getAllItemsGivenAccountId(Integer accountId){
        return this.itemRepo.findAllByAccountId(accountId);
    }

    // /account/1/item
    public Item createItem(Item item, Integer accountId){
        //verify accountId is found
        Account account = this.accountRepo.findById(accountId).orElse(null);

        if(account == null)
            return null;

        item.setAccount(account);

        return this.itemRepo.save(item);
    }

    public Item markItemInCart(Integer itemId){
        Item item = this.itemRepo.findById(itemId).orElse(null);

        if(item == null)
            return null;

        //toggling in cart or not
        item.setInCart(!item.getInCart());

        return this.itemRepo.save(item);
    }

    // /account/1/item/1
    public Boolean removeItemFromList(Integer accountId, Integer itemId){
        //check if account id is valid
        Account account = this.accountRepo.findById(accountId).orElse(null);

        if(account == null)
            return false;

        //check if account matches the account from id
        Item item = this.itemRepo.findById(itemId).orElse(null);

        if(item == null)
            return false;

        if(!account.getId().equals(item.getAccount().getId()))
            return false;

        this.itemRepo.deleteById(item.getId());
        return true;
    }

    public void removeItemsInCart(Integer accountId){
        this.itemRepo.deleteByAccountIdAndInCart(accountId, true);
    }



}
