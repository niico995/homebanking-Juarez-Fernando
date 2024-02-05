package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
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
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository){
		return args -> {
			Client melba = new Client( "Melba", "Morel", "melba@mindhub.com");
			System.out.println(melba);


			Account vin002 = new Account("VIN002",5000, LocalDate.now());
			Account vin001 = new Account("VIN001",7500, LocalDate.now().plusDays(1));



			melba.addAccount(vin002);
			melba.addAccount(vin001);
			clientRepository.save(melba);
			accountRepository.save(vin002);
			accountRepository.save(vin001);
			System.out.println(melba);

		};
	}

}
