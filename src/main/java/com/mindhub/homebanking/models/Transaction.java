package com.mindhub.homebanking.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private double amount;

    private String description;

    private LocalDate date;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "acc_id")
    private Account account;


    public Transaction(){

    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Transaction(double amount, String description, LocalDate date, TransactionType type) {
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", type=" + type +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", account=" + account +
                '}';
    }
}
