package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByNumber(String numAccount);
    Boolean existsAccountByNumber(String numAccount);

    //Boolean existsAccountByNumberAndClient(String numAccount, Client client);
    Boolean existsAccountByNumberAndCliente(String numAccount, Client cliente);

}
