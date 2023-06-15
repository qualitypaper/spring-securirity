package com.qualitypaper.co_tourism.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

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

  public String generateToken(Long id, UserDetails userDetails) {
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

}
