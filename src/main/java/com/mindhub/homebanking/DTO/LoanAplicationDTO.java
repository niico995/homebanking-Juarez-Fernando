package com.mindhub.homebanking.DTO;

public record LoanAplicationDTO(Long loanID, Double amount, Integer payments, String accountDestination) {
}
