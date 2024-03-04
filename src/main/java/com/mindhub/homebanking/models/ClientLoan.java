package com.mindhub.homebanking.models;

import jakarta.persistence.*;

@Entity
public class ClientLoan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;

    private Integer payments;

    @ManyToOne
    private Client client;

    @ManyToOne
    private Loan loan;

    public ClientLoan(){

    }

    public ClientLoan(double amount, Integer payments, Client client, Loan loan){
        this.amount = amount;
        this.payments = payments;
        this.client = client;
        this.loan = loan;
    }

    public ClientLoan(double amount, Integer payments) {
        this.amount = amount;
        this.payments = payments;
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

    public Integer getPayments() {
        return payments;
    }

    public void setPayments(Integer payments) {
        this.payments = payments;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    @Override
    public String toString() {
        return "ClientLoan{" +
                "id=" + id +
                ", amount=" + amount +
                ", payments=" + payments +
                ", client=" + client +
                ", loan=" + loan +
                '}';
    }
}
