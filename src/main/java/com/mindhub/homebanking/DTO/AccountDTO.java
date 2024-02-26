package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.models.Account;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class AccountDTO {

    private Long id;

    private double balance;

    private String number;

    private LocalDate creationDate;

    private List<TransactionDTO> transactions;

    public AccountDTO(){

    }

    public AccountDTO(Account account){
        this.id = account.getId();
        this.balance = account.getBalance();
        this.number = account.getNumber();
        this.creationDate = account.getCreationDate();
        this.transactions = account.getTransactions().stream().map(TransactionDTO::new).collect(Collectors.toList());
    }

    public String getNumber() {
        return number;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Long getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }

    public List<TransactionDTO> getTransactions() {
        return transactions;
    }
}
