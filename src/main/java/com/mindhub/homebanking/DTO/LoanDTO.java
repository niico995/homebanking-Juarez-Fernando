package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.models.Loan;

import java.util.List;
import java.util.Set;

public class LoanDTO {

    private Long id;
    private String name;
    private Double maxAcount;
    private Set<Integer> payments;
    private List<ClientLoanDTO> clientLoans;
    private Long client;

    public LoanDTO(Loan loan) {
        this.id = loan.getId();
        this.name = loan.getName();
        this.maxAcount = loan.getMaxAccount();
        this.payments = loan.getPayments();
        this.clientLoans = loan.getClientLoans().stream().map(ClientLoanDTO::new).toList();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getMaxAcount() {
        return maxAcount;
    }

    public Set<Integer> getPayments() {
        return payments;
    }

    public List<ClientLoanDTO> getClientLoans() {
        return clientLoans;
    }
}
