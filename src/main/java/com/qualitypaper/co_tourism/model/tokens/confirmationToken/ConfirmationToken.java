package com.qualitypaper.co_tourism.model.tokens.confirmationToken;

import com.qualitypaper.co_tourism.model.tokens.TokenType;
import com.qualitypaper.co_tourism.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name="confirmation_token")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String confirmationToken;
    @Enumerated(EnumType.STRING)
    private TokenType tokenType = TokenType.CONFIRMATION;
    private LocalDateTime created;
    private LocalDateTime expires;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;
}
