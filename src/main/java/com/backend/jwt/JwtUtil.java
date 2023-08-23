package com.backend.jwt;

import com.backend.exception.APIException;
import com.backend.model.RefreshToken;
import com.backend.repository.RefreshRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecretKey;

    @Value("${jwt.expiry}")
    private long jwtExpiry;

    @Autowired
    private RefreshRepository refreshRepository;

    public String generateToken(UserDetails userDetails) {

        Date now = new Date();
        Date expiry = new Date(now.getTime() + jwtExpiry * 1000);
        Map<String, Object> claims = new HashMap<>();
        claims.put("myRoles", getRoles(userDetails));
        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expiry)
                .signWith(SignatureAlgorithm.HS512, jwtSecretKey)
                .compact();

        return token;
    }

    public String getUsernameFromToken(String token) {
        Claims claim = Jwts.parser()  // Use parser() instead of parserBuilder()
                .setSigningKey(key())
                .parseClaimsJws(token)   // Parse as JWS token
                .getBody();
        return claim.getSubject();
    }

    public boolean validate(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parse(token);
            return true;
        } catch (MalformedJwtException ex) {
            throw new APIException(HttpStatus.UNAUTHORIZED,"Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new APIException(HttpStatus.UNAUTHORIZED,"Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new APIException(HttpStatus.UNAUTHORIZED,"Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new APIException(HttpStatus.UNAUTHORIZED,"JWT claims string is empty.");
        } catch (SignatureException exception){
            throw new APIException(HttpStatus.UNAUTHORIZED,"JWT signature not matching");
        }
    }

    private Set<String> getRoles(UserDetails user) {
        return user.getAuthorities().stream().map(role -> role.getAuthority()).collect(Collectors.toSet());
    }


    public String generateRefreshToken(String email) {

        RefreshToken refreshToken = new RefreshToken();
        String rf = UUID.randomUUID().toString();
        refreshToken.setToken(rf);
        refreshToken.setEmail(email);
        Instant expirationTime = Instant.now().plus(30, ChronoUnit.DAYS);
        refreshToken.setExpiry(expirationTime);

        return refreshRepository.save(refreshToken).getToken();
    }


    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecretKey));
    }
}
