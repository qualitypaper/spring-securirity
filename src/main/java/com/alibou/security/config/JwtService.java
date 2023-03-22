package com.alibou.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {

  private final JwtEncoder jwtEncoder;
  private final JwtDecoder jwtDecoder;

  private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

  public String extractUsername(String token) {
    return extractClaim(token, "sub");
  }

  public <T> T extractClaim(String token, String claim) {
    final Map<String , Object> claims= extractAllClaims(token);
    return (T) claims.get(claim);
  }

  public String generateToken(UserDetails userDetails, Long id) {
    return generateToken(id, userDetails);
  }

  public String generateToken(
          Long id,
          UserDetails userDetails
  ) {
    Instant now = Instant.now();
    JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuedAt(now)
            .issuer("daltter")
            .subject(userDetails.getUsername())
            .claim("id", id)
            .expiresAt(now.plus(1, ChronoUnit.HOURS))
            .build();
    System.out.println(this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue());
    return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
  }

  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).isBefore(Instant.now());
  }

  private Instant extractExpiration(String token) {
    return extractClaim(token, "exp");
  }

  private Map<String, Object> extractAllClaims(String token) {
    Map<String, Object> claims = jwtDecoder.decode(token).getClaims();
    System.out.println(claims);
    return claims;
  }

  private Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
