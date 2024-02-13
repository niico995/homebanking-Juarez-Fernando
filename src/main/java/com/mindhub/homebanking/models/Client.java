package com.mindhub.homebanking.models;

import com.mindhub.homebanking.DTO.ClientLoanDTO;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private String name, lastName, email;

    @OneToMany(mappedBy="cliente", fetch = FetchType.EAGER)
    Set<Account> accounts = new HashSet<>();

    @OneToMany(mappedBy = "client")
    private Set<ClientLoan> clientLoans = new HashSet<>();

    @OneToMany(mappedBy = "client")
    private List<Card> cards = new ArrayList<>();



    public Client(){

    }

    public Client(String name, String lastName, String email) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account account){
        account.setCliente(this);
        accounts.add(account);
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }

    public void setClientLoans(Set<ClientLoan> clientLoans) {
        this.clientLoans = clientLoans;
    }

    public void addClientLoans(ClientLoan clientLoan){
        clientLoan.setClient(this);
        clientLoans.add(clientLoan);
    }

    public List<ClientLoanDTO> getLoans(){
        return this.getClientLoans().stream().map(ClientLoanDTO::new).toList();
    }

    public List<Card> getCard() {
        return cards;
    }

    public void setCard(List<Card> card) {
        this.cards = card;
    }

    public void addCard(Card card){
        card.setClient(this);
        cards.add(card);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", accounts=" + accounts +
                ", cards=" + cards +
                '}';
    }

}
