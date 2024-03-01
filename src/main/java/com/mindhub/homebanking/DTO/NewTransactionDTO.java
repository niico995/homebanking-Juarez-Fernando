package com.mindhub.homebanking.DTO;

public record NewTransactionDTO(Double amount, String detail, String numberOrigin, String numberDestination)  {
}
