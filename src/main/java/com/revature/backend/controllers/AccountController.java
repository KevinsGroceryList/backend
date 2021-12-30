package com.revature.backend.controllers;

import com.revature.backend.models.Account;
import com.revature.backend.models.JsonResponse;
import com.revature.backend.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// /account
@RestController
@RequestMapping(value = "account")
@CrossOrigin(origins = "http://localhost:4200")
public class AccountController {

    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<JsonResponse> registerAccount(@RequestBody Account credentials){
        Account account = this.accountService.createAccount(credentials);

        //username is taken
        if(account == null)
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new JsonResponse("username is already taken", null));

        //account successfully created
        return ResponseEntity.ok(new JsonResponse("account created", account));

    }
}
