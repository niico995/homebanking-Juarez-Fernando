package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.DTO.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin(origins = "http://localhost:5173")
public class AccountController {

    public static int number(){
        Random rand = new Random();
        int randomNumber = rand.nextInt(9000) +1000;
        return randomNumber;
    }
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

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

    @PostMapping("/")
    public ResponseEntity<?> addAccount(){

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientRepository.findByEmail(email);

        if (client.getAccounts().size() == 3){
            return new ResponseEntity<>("The maximum of three accounts has already been reached.",HttpStatus.FORBIDDEN);
        }


        Account newAccount = new Account("VIN-" + number(),  0.0, LocalDate.now());
        client.addAccount(newAccount);
        accountRepository.save(newAccount);
        clientRepository.save(client);

        return new ResponseEntity<>("Successfully created", HttpStatus.CREATED);
    }
}
