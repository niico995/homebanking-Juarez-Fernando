package com.mindhub.homebanking.repositories;
import java.util.List;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByCardHolder(String cardHolder);
    //Boolean existsCardByTypeAndColorAndClient(TransactionType cardType, CardColor color, Client client);

    //int countByTypeAndClient(TransactionType cardType, Client client);

    //int countByCardTypeAndClient(TransactionType cardType, Client client);
}
