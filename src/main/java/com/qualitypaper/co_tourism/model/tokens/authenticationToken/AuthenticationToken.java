package com.qualitypaper.co_tourism.model.tokens.authenticationToken;

import com.qualitypaper.co_tourism.model.tokens.TokenType;
import com.qualitypaper.co_tourism.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="authentication_token")
public class AuthenticationToken {

  @Id
  @GeneratedValue
  private Long id;

  @Column(unique = true, length = 1028)
  private String token;

  @Enumerated(EnumType.STRING)
  private TokenType tokenType = TokenType.BEARER;

  private boolean revoked;

  private boolean expired;
  private LocalDateTime created;
  private LocalDateTime lastLogin;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;
}
