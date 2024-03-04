package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.LoginDTO;
import com.mindhub.homebanking.DTO.RegisterDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.securityServices.JwtUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.mindhub.homebanking.repositories.AccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Random;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    public static int number(){
        Random rand = new Random();
        int randomNumber = rand.nextInt(9000) +1000;
        return randomNumber;
    }
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtilService jwtUtilService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountRepository accountRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.email(),loginDTO.password()));
            final UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.email());
            final String jwt = jwtUtilService.generateToken(userDetails);
            return ResponseEntity.ok(jwt);
        }catch (Exception e) {
            return new ResponseEntity<>("Incorrect", HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO){



            if (registerDTO.email().isBlank()){
                return new ResponseEntity<>("Email has no content", HttpStatus.BAD_REQUEST);
            }

            if (!registerDTO.email().contains("@")){
                return new ResponseEntity<>("Invalid email", HttpStatus.BAD_REQUEST);
            }

            if(clientRepository.findByEmail(registerDTO.email()) != null){
                return new ResponseEntity<>("Email is already registered", HttpStatus.FORBIDDEN);
            }

            if (registerDTO.password().isBlank()){
                return new ResponseEntity<>("Password has no content", HttpStatus.BAD_REQUEST);
            }

            if (registerDTO.password().length() < 8){
                return new ResponseEntity<>("Enter a password longer than 8 digits", HttpStatus.BAD_REQUEST);
            }

            if (registerDTO.name().isBlank()){
                return new ResponseEntity<>("Name has no content", HttpStatus.BAD_REQUEST);
            }

            if (registerDTO.lastname().isBlank()){
                return new ResponseEntity<>("Lastname has no content", HttpStatus.BAD_REQUEST);
            }

            Client newClient = new Client(registerDTO.name(),
                    registerDTO.lastname(),
                    registerDTO.email(),
                    passwordEncoder.encode(registerDTO.password()));
            clientRepository.save(newClient);



            String numNewAccount;

            do{
                numNewAccount = "VIN-" + number();
            }while (accountRepository.findByNumber(numNewAccount) != null);

            Account newAccount = new Account(numNewAccount , 0.0, LocalDate.now());
            newClient.addAccount(newAccount);
            accountRepository.save(newAccount);
            clientRepository.save(newClient);

            return new ResponseEntity<>("Successfully created", HttpStatus.CREATED);

    }



}
