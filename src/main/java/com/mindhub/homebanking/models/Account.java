package com.mindhub.homebanking.models;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client cliente;
    private String number;

    private double balance;

    private LocalDate creationDate;

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    Set<Transaction> transaction = new HashSet<>();

    public Account(){

    }

    public Account(String number, double balance, LocalDate creationDate) {
        this.number = number;
        this.balance = balance;
        this.creationDate = creationDate;
    }

    public void setCliente(Client cliente) {
        this.cliente = cliente;
    }

    public Client getCliente() {
        return cliente;
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public double getBalance() {
        return balance;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Set<Transaction> getTransactions() {
        return transaction;
    }

    public void addTransaction(Transaction transaction){
        transaction.setAccount(this);
        this.transaction.add(transaction);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", balance=" + balance +
                ", creationDate=" + creationDate +
                '}';
    }
}
