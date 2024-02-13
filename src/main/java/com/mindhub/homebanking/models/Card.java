package com.mindhub.homebanking.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number,cardholder;

    private CardColor cardColor;

    private TransactionType cardType;
    private int cvv;

    private LocalDate fromDate, thruDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;



    public Card(){

    }

    public Card(String cardholder, CardColor cardColor, TransactionType cardType, int cvv, String number, LocalDate fromDate, LocalDate thruDate) {
        this.cardholder = cardholder;
        this.cardColor = cardColor;
        this.cardType = cardType;
        this.cvv = cvv;
        this.number = number;
        this.fromDate = fromDate;
        this.thruDate = thruDate;
    }


    public Long getId() {
        return id;
    }

    public String getCardholder() {
        return cardholder;
    }

    public void setCardholder(String cardholder) {
        this.cardholder = cardholder;
    }

    public CardColor getCardColor() {
        return cardColor;
    }

    public void setCardColor(CardColor cardColor) {
        this.cardColor = cardColor;
    }

    public TransactionType getCardType() {
        return cardType;
    }

    public void setCardType(TransactionType cardType) {
        this.cardType = cardType;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", cardholder='" + cardholder + '\'' +
                ", cardColor='" + cardColor + '\'' +
                ", cardType='" + cardType + '\'' +
                ", cvv=" + cvv +
                ", number=" + number +
                ", fromDate=" + fromDate +
                ", thruDate=" + thruDate +
                '}';
    }

}
