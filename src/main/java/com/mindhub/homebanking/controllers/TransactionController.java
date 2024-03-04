package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.AccountDTO;
import com.mindhub.homebanking.DTO.NewTransactionDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    @PostMapping("/")
    public ResponseEntity<?> addTransaction(@RequestBody NewTransactionDTO addTransactionDTO){

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientRepository.findByEmail(email);

        if (addTransactionDTO.numberOrigin().isBlank()){
            return new ResponseEntity<>("Number account origin is empty", HttpStatus.FORBIDDEN);
        }

        if (addTransactionDTO.detail().isBlank()){
            return new ResponseEntity<>("Description is empty",HttpStatus.FORBIDDEN);
        }

        if (addTransactionDTO.amount() == null){
            return new ResponseEntity<>("Amount is empty",HttpStatus.FORBIDDEN);
        }

        if (addTransactionDTO.numberOrigin().equals(addTransactionDTO.numberDestination())){
            return new ResponseEntity<>("Account origin is equals account destination",HttpStatus.FORBIDDEN);
        }

        if (!accountRepository.existsAccountByNumber(addTransactionDTO.numberOrigin())){
            return new ResponseEntity<>("The account origin not exist", HttpStatus.FORBIDDEN);
        }

        if (!accountRepository.existsAccountByNumberAndClient(addTransactionDTO.numberOrigin(), client)){
            return new ResponseEntity<>("The account origin not is your account", HttpStatus.FORBIDDEN);
        }

        if (!accountRepository.existsAccountByNumber(addTransactionDTO.numberOrigin())){
            return new ResponseEntity<>("The account destination not exist", HttpStatus.FORBIDDEN);
        }

        Account accountOrigin = accountRepository.findByNumber(addTransactionDTO.numberOrigin());

        if(addTransactionDTO.amount() > accountOrigin.getBalance()){
            return new ResponseEntity<>("The available balance is insufficient", HttpStatus.FORBIDDEN);
        }


        Transaction DebitTransaction = new Transaction(addTransactionDTO.amount(),
                addTransactionDTO.detail()+ " to " + addTransactionDTO.numberDestination(),
                LocalDate.now(),
                TransactionType.DEBIT
                );

        accountOrigin.setBalance((accountOrigin.getBalance() - addTransactionDTO.amount()));
        accountOrigin.addTransaction(DebitTransaction);
        transactionRepository.save(DebitTransaction);
        accountRepository.save(accountOrigin);

        Account accountDestination = accountRepository.findByNumber(addTransactionDTO.numberDestination());

        Transaction CreditTransaction = new Transaction(addTransactionDTO.amount(),
                addTransactionDTO.detail()+ " to " + addTransactionDTO.numberDestination(),
                LocalDate.now(),
                TransactionType.CREDIT
        );

        accountDestination.setBalance((accountDestination.getBalance() + addTransactionDTO.amount()));
        accountDestination.addTransaction(CreditTransaction);
        transactionRepository.save(CreditTransaction);
        accountRepository.save(accountDestination );

        return new ResponseEntity<>("Transaction completed successfully", HttpStatus.CREATED);
    }



    @GetMapping("/clients/current/transactions")
    public ResponseEntity<?> getTransactions(){

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientRepository.findByEmail(userEmail);
        Set<Account> accounts = new HashSet<>();
        accounts = client.getAccounts();

        return ResponseEntity.ok(accounts.stream().map(AccountDTO::new).toList());

    }

}
