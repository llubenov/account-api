package com.qa.account.accountapi.controller;


import com.qa.account.accountapi.domain.Account;
import com.qa.account.accountapi.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;


    @GetMapping("/accounts")
    public List<Account> retrieveAllAccounts() {
        return accountRepository.findAll();
    }

    @GetMapping("/account/{id}")
    public Account retrieveAccount(@PathVariable Long id) {
        Optional<Account> account = accountRepository.findById(id);

        return account.get();
    }

    @DeleteMapping("/deleteAccount/{id}")
    public void deleteAccount(@PathVariable long id) {
        accountRepository.deleteById(id);
    }

    @PostMapping("/createAccount")
    public ResponseEntity<Object> createAccount(@RequestBody Account account) {
        Account savedAccount = accountRepository.save(account);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedAccount.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/updateAccount/{id}")
    public ResponseEntity<Object> updateAccount(@RequestBody Account account, @PathVariable long id) {

        Optional<Account> accountToUpdate = accountRepository.findById(id);

        if (!accountToUpdate.isPresent())
            return ResponseEntity.notFound().build();

        account.setId(id);

        accountRepository.save(account);

        return ResponseEntity.noContent().build();
    }

}