package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.TransactionType;

import java.time.LocalDate;

public class CardDTO {

    private Long id;

    private String number,cardholder;


    private CardColor cardColor;

    private TransactionType cardType;
    private int cvv;

    private LocalDate fromDate, thruDate;


    public CardDTO(Card card){
        this.id = card.getId();
        this.number = card.getNumber();
        this.cardholder = card.getCardholder();
        this.cardColor = card.getCardColor();
        this.cardType = card.getCardType();
        this.cvv = card.getCvv();
        this.fromDate = card.getFromDate();
        this.thruDate = card.getThruDate();
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public String getCardholder() {
        return cardholder;
    }

    public CardColor getCardColor() {
        return cardColor;
    }

    public TransactionType getCardType() {
        return cardType;
    }

    public int getCvv() {
        return cvv;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }
}
