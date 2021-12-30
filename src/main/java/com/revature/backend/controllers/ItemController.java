package com.revature.backend.controllers;

import com.revature.backend.models.Item;
import com.revature.backend.models.JsonResponse;
import com.revature.backend.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "account/{accountId}/item")
@CrossOrigin(origins = "http://localhost:4200")
public class ItemController {

    private ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService){
        this.itemService = itemService;
    }

    /*
    *  /account/1/item POST  ---create item for account
    *  /account/1/item DELETE --- remove all items in cart
    *  /account/1/item/1 DELETE --- remove 1 item
    *  /account/1/item GET --- get all items given account
    *  /account/1/item/1 PATCH --- mark item in cart
    * */

    @GetMapping
    public ResponseEntity<JsonResponse> getAllItemsForAccount(@PathVariable Integer accountId){
        return ResponseEntity
                .ok(new JsonResponse("getting all items for account id " + accountId, this.itemService.getAllItemsGivenAccountId(accountId)));
    }


    @PostMapping
    public ResponseEntity<JsonResponse> addItem(@PathVariable Integer accountId, @RequestBody Item requestBody){
        Item item = this.itemService.createItem(requestBody,accountId);

        if(item == null)
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new JsonResponse("account with id " + accountId + " doesnt exists", null));

        return ResponseEntity.ok(new JsonResponse("item created", item));
    }
}
