package com.qualitypaper.co_tourism.model.user;

import com.qualitypaper.co_tourism.model.tokens.authenticationToken.AuthenticationToken;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Setter
@Getter
@Table(name = "_user")
public class User implements UserDetails {

  @Id
  @GeneratedValue
  private Long id;
  private String fullName;
  private String phoneNumber;
  private String email;
  private String password;
  @Enumerated(EnumType.STRING)
  private Role role;

  @OneToMany(mappedBy = "user")
  private List<Review> reviews;

  @OneToMany(mappedBy = "user")
  private List<AuthenticationToken> authenticationTokens;
  private LocalDateTime createdAt;
  private boolean confirmed;
  private boolean banned;
  private LocalDateTime lastLogin;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return !banned;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return confirmed;
  }


}
