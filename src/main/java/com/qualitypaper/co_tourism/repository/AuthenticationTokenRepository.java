package com.qualitypaper.co_tourism.repository;

import java.util.List;
import java.util.Optional;

import com.qualitypaper.co_tourism.model.tokens.authenticationToken.AuthenticationToken;
import com.qualitypaper.co_tourism.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AuthenticationTokenRepository extends JpaRepository<AuthenticationToken, Integer> {

  @Query(value = """
      select t from AuthenticationToken t inner join User u\s
      on t.user.id = u.id\s
      where u.id = :id and (t.expired = false or t.revoked = false)\s
      """)
  List<AuthenticationToken> findAllValidTokenByUser(Long id);

  Optional<AuthenticationToken> findByToken(String authenticationToken);
  Optional<AuthenticationToken> findTokenByUserAndToken(User user, String authenticationToken);
}
