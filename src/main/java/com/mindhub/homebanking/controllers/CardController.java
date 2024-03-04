package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;



public class CardController {


    public static int cvv(){
        Random rand = new Random();
        int randomNumber = rand.nextInt(900) + 100;
        return randomNumber;
    }
    public static int number(){
        Random rand = new Random();
        int randomNumber = rand.nextInt(9000) +1000;
        return randomNumber;
    }

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private ClientRepository clientRepository;

   /* @GetMapping("/")
    public ResponseEntity<?> getAllCards(){

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientRepository.findByEmail(email);

        String cardHolder = client.getName() + " " + client.getLastName();
        List<Card> cards = cardRepository.findByCardHolder(cardHolder);
        return new ResponseEntity<>(cards.stream().map(CardDTO::new).collect(Collectors.toList()), HttpStatus.OK);
    } */

    @GetMapping("/clients/current/cards")
    public ResponseEntity<?> getCards(){
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientRepository.findByEmail(userEmail);
        Set<Card> cards = new HashSet<>();
        cards = client.getCard();

        return ResponseEntity.ok(cards.stream().map(CardDTO::new).toList());
    }


    @PostMapping("/")
    private ResponseEntity<?> addCard(@RequestBody CardDTO cardDTO){

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientRepository.findByEmail(email);

        Set<Card> cards = client.getCard();

        Set<Boolean> existTypeColor = cards
                .stream()
                .map(card -> (card.getCardType() == cardDTO.getCardType() && card.getCardColor() == cardDTO.getCardColor()))
                .collect(Collectors.toSet());

        if (existTypeColor.contains(true)){
            return new ResponseEntity<>("You already have a card of type " + cardDTO.getCardType().toString().toLowerCase() +" with the color " + cardDTO.getCardColor().toString().toLowerCase(), HttpStatus.FORBIDDEN);
        }



        Card newCard = new Card(client.getName(),
                cardDTO.getCardColor(),
                cardDTO.getCardType(),
                cvv(),
                (4045+"-"+number()+"-"+number()+"-"+number()),
                LocalDate.now().plusYears(5) ,
                LocalDate.now());

        cardRepository.save(newCard);
        client.addCard(newCard);
        clientRepository.save(client);


        return new ResponseEntity<>("Successfully created", HttpStatus.CREATED);
    }
}
