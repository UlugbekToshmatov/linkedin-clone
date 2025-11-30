package com.linkedin.linkedin.models.token.mapper;

import com.linkedin.linkedin.enums.TokenType;
import com.linkedin.linkedin.models.token.entity.Token;
import com.linkedin.linkedin.models.user.entity.User;
import static com.linkedin.linkedin.jwt.JwtProviderParams.*;

import java.time.Duration;
import java.time.LocalDateTime;

public class TokenMapper {

    public static Token toToken(User user, String jwtToken, TokenType tokenType) {
        Long tokenExpiration = null;
        switch (tokenType) {
            case ACCESS_TOKEN -> tokenExpiration = ACCESS_TOKEN_EXPIRATION;
            case REFRESH_TOKEN -> tokenExpiration = REFRESH_TOKEN_EXPIRATION;
            case ACCOUNT_VERIFICATION_TOKEN -> tokenExpiration = ACCOUNT_VERIFICATION_TOKEN_EXPIRATION;
            case PASSWORD_RESET_TOKEN -> tokenExpiration = RESET_PASSWORD_TOKEN_EXPIRATION;
        }

        return Token.builder()
            .user(user)
            .jwtToken(jwtToken)
            .type(tokenType)
            .expiresAt(LocalDateTime.now().plus(Duration.ofMillis(tokenExpiration)))
            .revoked(Boolean.FALSE)
            .expired(Boolean.FALSE)
            .build();
    }

}
