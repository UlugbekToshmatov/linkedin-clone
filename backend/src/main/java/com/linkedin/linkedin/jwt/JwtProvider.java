package com.linkedin.linkedin.jwt;

import com.linkedin.linkedin.enums.ResponseStatus;
import com.linkedin.linkedin.enums.TokenType;
import com.linkedin.linkedin.exception_handler.ApiException;
import com.linkedin.linkedin.models.user.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.DecodingException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import io.jsonwebtoken.security.WeakKeyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.linkedin.linkedin.jwt.JwtProviderParams.*;

@Slf4j
@Service
public class JwtProvider {

    public String generateToken(User user, TokenType tokenType) {
        return switch (tokenType) {
            case ACCESS_TOKEN -> buildToken(new HashMap<>(), user, ACCESS_TOKEN_EXPIRATION);
            case REFRESH_TOKEN -> buildToken(new HashMap<>(), user, REFRESH_TOKEN_EXPIRATION);
            case ACCOUNT_VERIFICATION_TOKEN ->
                buildToken(new HashMap<>(), user, ACCOUNT_VERIFICATION_TOKEN_EXPIRATION);
            case PASSWORD_RESET_TOKEN -> buildToken(new HashMap<>(), user, RESET_PASSWORD_TOKEN_EXPIRATION);
        };
    }

    private String buildToken(
        Map<String, Object> extraClaims,
        User user,
        long tokenExpiration
    ) {
        extraClaims.put("userId", user.getId());

        return Jwts
            .builder()
            .setClaims(extraClaims)
            .setSubject(user.getEmail())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    public String extractUsernameFromToken(String token) {
        /* return extractClaim(token, claims -> (String) claims.get("sub")); */
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token) {
        return new Date(System.currentTimeMillis()).after(extractClaim(token, Claims::getExpiration));
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            log.error(e.getMessage());
            throw new ApiException(ResponseStatus.TOKEN_EXPIRED);
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException |
            IllegalArgumentException e) {
            log.error(e.getMessage());
            throw new ApiException(ResponseStatus.INVALID_TOKEN);
        }
    }

    private Key getSigningKey() {
        try {
            byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
            return Keys.hmacShaKeyFor(keyBytes);
        } catch (DecodingException | WeakKeyException e) {
            log.error(e.getMessage());
            throw new ApiException(ResponseStatus.TOKEN_PROCESSING_ERROR);
        }
    }
}
