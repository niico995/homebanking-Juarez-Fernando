package com.mindhub.homebanking.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtUtilService {

    private static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();

    public static final long JWT_TOKEN_VALIDITY = 1000 * 60 * 60;

    public Claims extraactAllClaims(String token){
        return Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token).getPayload();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
            final Claims claims = extraactAllClaims(token);
            return claimsResolver.apply(claims);
    }

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    public Boolean isTokenExpired(String token){
        return extractExpiration(token).before((new Date()));
    }

    

}
