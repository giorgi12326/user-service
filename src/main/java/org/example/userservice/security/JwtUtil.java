package org.example.userservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
    String secret = "h1FnJATLKth8JHPkj9V7kihBNa9U5yUBpEboABt2dUY";
    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .signWith(getKey())
                .issuedAt(new Date(System.currentTimeMillis()))
                .issuedAt(new Date(System.currentTimeMillis() + 1000*60*60*24))
                .compact();
    }

    private SecretKey getKey() {
        byte[] bytes = secret.getBytes();
        return Keys.hmacShaKeyFor(bytes);
    }

    public Claims getClaimsAndVerifySignature(String token){
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getUsernameFromToken(String token) {
        return getClaimsAndVerifySignature(token).getSubject();
    }

}
