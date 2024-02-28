package com.mindhub.homebanking.repositories;
import java.util.List;
import com.mindhub.homebanking.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByCardHolder(String cardHolder);
}
