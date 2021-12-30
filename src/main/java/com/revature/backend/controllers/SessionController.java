package com.revature.backend.controllers;

import com.revature.backend.models.Account;
import com.revature.backend.models.JsonResponse;
import com.revature.backend.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "session")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class SessionController {

    private AccountService accountService;

    @Autowired
    public SessionController(AccountService accountService){
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<JsonResponse> loginAccount(HttpSession httpSession, @RequestBody Account credentials){
        Account account = this.accountService.validateAccountCredentials(credentials);

        if(account == null)
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new JsonResponse("invalid username or password", null));


        httpSession.setAttribute("user-session", account.getId());
        return ResponseEntity.ok(new JsonResponse("login successful", account));
    }

    @DeleteMapping
    public ResponseEntity<JsonResponse> logoutAccount(HttpSession httpSession){
        httpSession.invalidate();

        return ResponseEntity.ok(new JsonResponse("session terminated", null));
    }

    @GetMapping
    public ResponseEntity<JsonResponse> checkAccountSession(HttpSession httpSession){
        Integer accountId = (Integer) httpSession.getAttribute("user-session");

        //DEF NOT OVERKILL
        if(accountId == null)
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new JsonResponse("no session found", null));

        Account account = this.accountService.getAccountById(accountId);

        //bad id
        if(account == null)
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new JsonResponse("no session found", null));

        return ResponseEntity.ok(new JsonResponse("session found", account));

    }


}
