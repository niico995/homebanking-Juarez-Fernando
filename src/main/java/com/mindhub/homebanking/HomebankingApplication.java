package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository){
		return args -> {
			Client melba = new Client( "Melba", "Morel", "melba@mindhub.com");
			System.out.println(melba);


			Account vin002 = new Account("VIN002",5000, LocalDate.now());
			Account vin001 = new Account("VIN001",7500, LocalDate.now().plusDays(1));

			Transaction trans1 = new Transaction(1000, "Prubas MindHub",LocalDate.now(),TransactionType.CREDIT);
			Transaction trans2 = new Transaction(2500, "Prubas MindHub 2",LocalDate.now().plusDays(1),TransactionType.DEBIT);

			melba.addAccount(vin002);
			melba.addAccount(vin001);

			vin002.addTransaction(trans1);
			vin001.addTransaction(trans2);

			clientRepository.save(melba);
			accountRepository.save(vin002);
			accountRepository.save(vin001);
			transactionRepository.save(trans1);
			transactionRepository.save(trans2);
			System.out.println(melba);

		};
	}

}
