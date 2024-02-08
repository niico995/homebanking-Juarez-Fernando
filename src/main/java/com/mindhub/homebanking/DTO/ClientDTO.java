package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.models.Client;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {

    private Long id;
    private String name, lastName,email;

    private Set<AccountDTO> accounts;

    private List<ClientLoanDTO> loans;

    public ClientDTO(){

    }

    public ClientDTO(Client cliente){
        this.id = cliente.getId();
        this.name = cliente.getName();
        this.lastName =  cliente.getLastName();
        this.email = cliente.getEmail();
        this.accounts = cliente.getAccounts().stream().map(AccountDTO::new).collect(Collectors.toSet());
        this.loans = cliente.getClientLoans().stream().map(ClientLoanDTO::new).collect(Collectors.toList());
    }

    public String getLastName() {
        return lastName;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<AccountDTO> getAccounts() {
        return accounts;
    }

    public String getEmail() {
        return email;
    }

    public List<ClientLoanDTO> getLoans() {
        return loans;
    }
}
