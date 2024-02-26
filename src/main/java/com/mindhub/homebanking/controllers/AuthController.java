package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.LoginDTO;
import com.mindhub.homebanking.DTO.RegisterDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.JwtUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

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

            if(registerDTO.name().isBlank() || registerDTO.lastname().isBlank() || registerDTO.password().isBlank() || registerDTO.email().isBlank()){
                return new ResponseEntity<>("No data in field", HttpStatus.NOT_FOUND);
            }

            Client newClient = new Client(registerDTO.name(),registerDTO.lastname(),registerDTO.email(),registerDTO.password());
            clientRepository.save(newClient);

            return new ResponseEntity<>("Created", HttpStatus.CREATED);

    }

}
