package com.project.mypocketpurse.repository;

import com.project.mypocketpurse.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AccountRepository
        extends JpaRepository<Account, Long> {

    List<Account> findAccountsByUserId(Long id);
    @Modifying
    @Query(nativeQuery = true,
            value = "update accounts set amount = amount - ?1 where id = ?2")
    void decreaseAmountByAction(Double amount, Long id);

    @Modifying
    @Query(nativeQuery = true,
            value = "update accounts set amount = amount + ?1 where id = ?2")
    void increaseAmountByAction(Double amount, Long id);
}