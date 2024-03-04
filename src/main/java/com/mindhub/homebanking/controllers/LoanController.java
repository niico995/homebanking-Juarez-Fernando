package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.LoanAplicationDTO;
import com.mindhub.homebanking.DTO.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/loan")
@CrossOrigin(origins = "http://localhost:5173")
public class LoanController {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private ClientLoanRepository clientLoanRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping("/")
    public List<LoanDTO> getLoans(){
        return loanRepository.findAll().stream().map(LoanDTO::new).toList();
    }

    @Transactional
    @PostMapping("/clients/current/loan")
    public ResponseEntity<?> addLoan(@RequestBody LoanAplicationDTO loanApplicationDTO){

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientRepository.findByEmail(userEmail);
        List<String> clientAccounts = client.getAccounts().stream().map(Account::getNumber).toList();
        List<LoanDTO> loans = loanRepository.findAll().stream().map(LoanDTO::new).toList();
        Loan loan = loanRepository.findById(loanApplicationDTO.loanID()).orElse(null);



        if(loanApplicationDTO.loanID().describeConstable().isEmpty() || loanApplicationDTO.loanID() == null){
            return new ResponseEntity<>("You have to complete the field 'loanID'", HttpStatus.FORBIDDEN);
        }

        if(loanApplicationDTO.amount() == null || loanApplicationDTO.amount() <= 0){
            return new ResponseEntity<>("You have to complete the field 'amount' with a number larger than 0", HttpStatus.FORBIDDEN);
        }

        if(loanApplicationDTO.payments() == null || loanApplicationDTO.payments() <= 0){
            return new ResponseEntity<>("You have to complete the field 'payments' and larger than 0", HttpStatus.FORBIDDEN);
        }

        if(loanApplicationDTO.accountDestination().isBlank()){
            return new ResponseEntity<>("You have to complete the field 'accountDestination'", HttpStatus.FORBIDDEN);
        }

        if(loan == null){
            return new ResponseEntity<>("There isn't any loan with the id " + loanApplicationDTO.loanID(), HttpStatus.FORBIDDEN);
        }

        if(loanApplicationDTO.amount() > loan.getMaxAccount()){
            return new ResponseEntity<>("The max amount you can ask in the loan " + loan.getName() + " is" + loan.getMaxAccount(), HttpStatus.FORBIDDEN);
        }

        if(!loan.getPayments().contains(loanApplicationDTO.payments())){
            return new ResponseEntity<>("The payments available in the loan " + loan.getName() + " are" + loan.getPayments(), HttpStatus.FORBIDDEN);
        }


        if(accountRepository.findByNumber(loanApplicationDTO.accountDestination()) == null){
            return new ResponseEntity<>("The account " + loanApplicationDTO.accountDestination() + " doesn't exist", HttpStatus.FORBIDDEN);

        }

        if(!clientAccounts.contains(loanApplicationDTO.accountDestination())){
            return new ResponseEntity<>("You can't ask a loan for the account " + loanApplicationDTO.accountDestination(), HttpStatus.FORBIDDEN);
        }

        Account account = accountRepository.findByNumber(loanApplicationDTO.accountDestination());

        ClientLoan clientLoan = new ClientLoan(loanApplicationDTO.amount() * 1.2, loanApplicationDTO.payments());
        Transaction transaction = new Transaction(loanApplicationDTO.amount(), loan.getName() + " loan approved", LocalDate.now(), TransactionType.CREDIT);

        client.addClientLoans(clientLoan);
        loan.addClientLoans(clientLoan);
        account.addTransaction(transaction);

        account.setBalance(account.getBalance() + loanApplicationDTO.amount());

        transactionRepository.save(transaction);
        accountRepository.save(account);
        clientLoanRepository.save(clientLoan);
        clientRepository.save(client);
        loanRepository.save(loan);

        return new ResponseEntity<>("CREATED", HttpStatus.CREATED);

    }

}
