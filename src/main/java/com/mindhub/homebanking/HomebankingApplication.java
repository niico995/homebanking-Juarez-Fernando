package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;

@SpringBootApplication
public class HomebankingApplication {


	public static int cvv(){
		Random rand = new Random();
		int randomNumber = rand.nextInt(900) + 100;
		return randomNumber;
	}

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository){
		return args -> {
			Client melba = new Client( "Melba", "Morel", "melba@mindhub.com");
			Client fernando = new Client("Fernando", "Juarez", "ferjuarez@mindhub.com");
			System.out.println(melba);


			Account vin002 = new Account("VIN002",5000, LocalDate.now());
			Account vin001 = new Account("VIN001",7500, LocalDate.now().plusDays(1));

			Transaction trans1 = new Transaction(1000, "Prubas MindHub",LocalDate.now(),TransactionType.CREDIT);
			Transaction trans2 = new Transaction(2500, "Prubas MindHub 2",LocalDate.now().plusDays(1),TransactionType.DEBIT);

			melba.addAccount(vin002);
			melba.addAccount(vin001);

			fernando.addAccount(vin002);

			vin002.addTransaction(trans1);
			vin001.addTransaction(trans2);


			Loan hipoteca = new Loan("Hipoteca", 500.000, Set.of(12,24,36,48,60));
			Loan personal = new Loan("Personal",100.000, Set.of(6,12,24));
			Loan automocion = new Loan("Automoción", 300.000, Set.of(6,12,24,36));

			ClientLoan clientLoan1 = new ClientLoan(400.000, 24, melba, hipoteca);
			ClientLoan clientLoan2 = new ClientLoan(100.000, 12, fernando,personal);
			ClientLoan clientLoan3 = new ClientLoan(150.000, 24, fernando, automocion);

			hipoteca.addClientLoans(clientLoan1);
			personal.addClientLoans(clientLoan2);
			automocion.addClientLoans(clientLoan3);

			melba.addClientLoans(clientLoan1);
			fernando.addClientLoans(clientLoan2);
			fernando.addClientLoans(clientLoan3);

			clientRepository.save(fernando);
			clientRepository.save(melba);
			accountRepository.save(vin002);
			accountRepository.save(vin001);
			transactionRepository.save(trans1);
			transactionRepository.save(trans2);
			loanRepository.save(hipoteca);
			loanRepository.save(personal);
			loanRepository.save(automocion);
			clientLoanRepository.save(clientLoan1);
			clientLoanRepository.save(clientLoan2);
			clientLoanRepository.save(clientLoan3);
			System.out.println(melba);
			System.out.println(fernando);
			LocalDate now = LocalDate.now();
			LocalDate trhu = LocalDate.now().plusYears(5);
			Card debitC = new Card(melba.getName()+" "+melba.getLastName(),CardColor.GOLD ,TransactionType.DEBIT, cvv(),"4005-4548-1457-3648", now, trhu);
			Card creditC = new Card(melba.getName()+" "+melba.getLastName(),CardColor.TITANIUM ,TransactionType.CREDIT, cvv(),"4045-1158-2238-2648", now, trhu);
			Card debitC2 = new Card(fernando.getName()+" "+fernando.getLastName(),CardColor.SILVER ,TransactionType.DEBIT, cvv(),"4255-4823-4572-4688", now, trhu);
			melba.addCard(debitC);
			melba.addCard(creditC);
			fernando.addCard(debitC2);
			cardRepository.save(debitC);
			cardRepository.save(debitC2);
			cardRepository.save(creditC);
			clientRepository.save(fernando);
			clientRepository.save(melba);
			System.out.println(debitC2);

		};
	}

}
