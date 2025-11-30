package com.linkedin.linkedin.models.token.repository;

import com.linkedin.linkedin.enums.TokenType;
import com.linkedin.linkedin.models.token.entity.Token;
import com.linkedin.linkedin.models.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
//    Optional<Token> findByUser_IdAndTypeAndRevokedFalseAndExpiredFalse(Long user_id, TokenType type);
    Optional<Token> findByUser_IdAndTypeAndRevokedFalseAndExpiredFalse(Long user_id, TokenType type);
    List<Token> findAllByUserAndTypeInAndRevokedFalseAndExpiredFalse(User user, Collection<TokenType> type);

    Optional<Token> findByJwtTokenAndType(String jwtToken, TokenType type);
    Optional<Token> findByJwtTokenAndTypeAndRevokedFalseAndExpiredFalse(String jwtToken, TokenType type);
}
