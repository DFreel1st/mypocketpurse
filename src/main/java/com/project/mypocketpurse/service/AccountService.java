package com.project.mypocketpurse.service;

import com.project.mypocketpurse.dto.AccountDTO;
import com.project.mypocketpurse.model.Account;
import com.project.mypocketpurse.model.User;
import com.project.mypocketpurse.repository.AccountRepository;
import com.project.mypocketpurse.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AccountService
        extends GenericService<Account, AccountDTO> {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountService(AccountRepository accountRepository,
                          UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Account update(Account object) {
        return accountRepository.save(object);
    }

    @Override
    public Account updateFromDTO(AccountDTO object, Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Account with such ID: " + id + " not found"));
        account.setName(object.getName());
        account.setAmount(object.getAmount());
        account.setDescription(object.getDescription());
        account.setCurrencyType(object.getCurrencyType());
        return accountRepository.save(account);
    }

    @Override
    public Account createFromDTO(AccountDTO newDtoObject) {
        Account newAccount = new Account();
        User user = userRepository.findUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        newAccount.setName(newDtoObject.getName());
        newAccount.setAmount(newDtoObject.getAmount());
        newAccount.setDescription(newDtoObject.getDescription());
        newAccount.setCurrencyType(newDtoObject.getCurrencyType());
        newAccount.setCreatedBy(user.getLogin());
        newAccount.setCreatedWhen(LocalDateTime.now());
        newAccount.setUser(user);
        return accountRepository.save(newAccount);
    }

    @Override
    public Account createFromEntity(Account newObject) {
        return accountRepository.save(newObject);
    }

    @Override
    public void delete(Long objectId) {
        Account account = accountRepository.findById(objectId)
                .orElseThrow(() -> new NotFoundException("Account with such ID: " + objectId + " not found"));
        accountRepository.delete(account);
    }

    @Override
    public Account getOne(Long objectId) {
        return accountRepository.findById(objectId)
                .orElseThrow(() -> new NotFoundException("Account with such ID: " + objectId + " not found"));
    }

    @Override
    public List<Account> listAll() {
        if (SecurityContextHolder.getContext().getAuthentication().getName().equals("admin")) {
            return accountRepository.findAll();
        }
        User user = userRepository.findUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        return accountRepository.findAccountsByUserId(user.getId());
    }
}