package com.revature.backend.repositories;

import com.revature.backend.models.Account;
import com.revature.backend.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepo extends JpaRepository<Item, Integer> {

    List<Item> findAllByAccountId(Integer accountId);

    void deleteByAccountIdAndInCart(Integer accountId,Boolean inCart);
}
