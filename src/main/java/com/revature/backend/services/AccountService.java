package com.revature.backend.services;

import com.revature.backend.models.Account;
import com.revature.backend.repositories.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class AccountService {

    private AccountRepo accountRepo;

    @Autowired
    public AccountService(AccountRepo accountRepo){
        this.accountRepo = accountRepo;
    }

    public Account getAccountById(Integer accountId){
        return this.accountRepo.findById(accountId).orElse(null);
    }

    public Account getAccountByUsername(String accountUsername){
        return this.accountRepo.findByUsername(accountUsername);
    }

    public Account createAccount(Account credentials){
        Account account = this.accountRepo.findByUsername(credentials.getUsername());

        //if account is NOT equal to null, then an account with the username provided already exists
        if(account != null)
            return null;

        return this.accountRepo.save(credentials);
    }

    public Account validateAccountCredentials(Account credentials){
        //check if username is in the system
        Account account = this.accountRepo.findByUsername(credentials.getUsername());

        //invalid credentials if username is not found
        if(account == null)
            return null;

        //check password
        if(!account.getPassword().equals(credentials.getPassword()))
            return null;

        //return account when credentials are correct
        return account;
    }
}
