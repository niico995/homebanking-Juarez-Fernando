package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.LoanDTO;
import com.mindhub.homebanking.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/loan")
@CrossOrigin(origins = "http://localhost:5173")
public class LoanController {

    @Autowired
    private LoanRepository loanRepository;

    @GetMapping("/")
    public List<LoanDTO> getLoans(){
        return loanRepository.findAll().stream().map(LoanDTO::new).toList();
    }

}
