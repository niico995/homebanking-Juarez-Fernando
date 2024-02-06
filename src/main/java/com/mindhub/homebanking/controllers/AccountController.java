package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.DTO.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/")
    public ResponseEntity<List<AccountDTO>> getAllAccounts(){
        List<Account> accounts = (List<Account>) accountRepository.findAll();

        return new ResponseEntity<>(accounts.stream().map(AccountDTO::new).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOneAccById(@PathVariable Long id){
        Account account = accountRepository.findById(id).orElse(null);

        if(account == null) {
            return new ResponseEntity<>("The ID does not match with our db, try again", HttpStatus.NOT_FOUND);
        }
        AccountDTO accountDTO = new AccountDTO(account);

        return new ResponseEntity<>(accountDTO, HttpStatus.OK);
    }
}
