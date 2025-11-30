package com.linkedin.linkedin.models.user.service;

import com.linkedin.linkedin.enums.ResponseStatus;
import com.linkedin.linkedin.enums.Status;
import com.linkedin.linkedin.enums.TokenType;
import com.linkedin.linkedin.exception_handler.ApiException;
import com.linkedin.linkedin.jwt.JwtProvider;
import com.linkedin.linkedin.models.token.entity.Token;
import com.linkedin.linkedin.models.token.repository.TokenRepository;
import com.linkedin.linkedin.models.user.dto.UserResponse;
import com.linkedin.linkedin.models.user.dto.auth.LoginRequest;
import com.linkedin.linkedin.models.user.dto.auth.LoginResponse;
import com.linkedin.linkedin.models.user.dto.auth.RegistrationRequest;
import com.linkedin.linkedin.models.user.entity.User;
import com.linkedin.linkedin.models.user.repository.UserRepository;
import com.linkedin.linkedin.utils.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.linkedin.linkedin.models.token.mapper.TokenMapper.toToken;
import static com.linkedin.linkedin.models.user.mapper.UserMapper.toEntity;
import static com.linkedin.linkedin.models.user.mapper.UserMapper.toResponse;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    @Value("${app.domain}")
    private String DOMAIN;


    @Transactional
    public UserResponse register(RegistrationRequest request) {
        if (userRepository.existsByEmail(request.email()))
            throw new ApiException(ResponseStatus.EMAIL_ALREADY_REGISTERED, request.email());

        User user = toEntity(request, passwordEncoder);
        userRepository.saveAndFlush(user);

        String accountVerificationToken = jwtProvider.generateToken(user, TokenType.ACCOUNT_VERIFICATION_TOKEN);
        tokenRepository.save(toToken(user, accountVerificationToken, TokenType.ACCOUNT_VERIFICATION_TOKEN));

        emailService.sendEmail(
            emailService.getAccountVerificationPayload(
                user, DOMAIN + "/api/v1/auth/verify-account?token=" + accountVerificationToken
            )
        );

        return toResponse(user);
    }

    @Transactional
    public void verifyAccount(String jwtToken) {
        if (jwtToken == null)
            throw new ApiException(ResponseStatus.TOKEN_REQUIRED);

        String username;
        try {
            username = jwtProvider.extractUsernameFromToken(jwtToken);
        } catch (ApiException e) {
            if (e.getResponseStatus().equals(ResponseStatus.TOKEN_EXPIRED)) {
                tokenRepository.findByJwtTokenAndTypeAndRevokedFalseAndExpiredFalse(jwtToken, TokenType.ACCOUNT_VERIFICATION_TOKEN)
                    .ifPresent(token -> {
                        token.setExpired(true);
                        tokenRepository.save(token);
                    });
            }
            throw e;
        }

        Token token = tokenRepository.findByJwtTokenAndType(jwtToken, TokenType.ACCOUNT_VERIFICATION_TOKEN)
            .orElseThrow(() -> new ApiException(ResponseStatus.TOKEN_NOT_FOUND));

        if (token.getExpired() || token.getRevoked())
            throw new ApiException(ResponseStatus.TOKEN_EXPIRED);

        User user = token.getUser();

        if (!username.equals(user.getEmail()))
            throw new ApiException(ResponseStatus.TOKEN_MISMATCH_EXCEPTION);

        token.setRevoked(true);
        tokenRepository.save(token);

        user.setStatus(Status.ACTIVE);
        userRepository.save(user);
    }

    @Transactional
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmailAndStatusNot(request.email().trim().toLowerCase(), Status.DELETED)
            .orElseThrow(() -> new ApiException(ResponseStatus.USER_NOT_FOUND, request.email()));

        if (user.getStatus() == Status.PENDING_VERIFICATION)
            throw new ApiException(ResponseStatus.USER_NOT_VERIFIED, request.email());

        if (user.getStatus() == Status.BLOCKED)
            throw new ApiException(ResponseStatus.USER_BLOCKED);

        if (!passwordEncoder.matches(request.password(), user.getPassword()))
            throw new ApiException(ResponseStatus.INCORRECT_PASSWORD);

        revokeCurrentActiveTokens(user);

        String newAccessToken = jwtProvider.generateToken(user, TokenType.ACCESS_TOKEN);
        String newRefreshToken = jwtProvider.generateToken(user, TokenType.REFRESH_TOKEN);

        saveNewTokens(user, newAccessToken, newRefreshToken);

        return new LoginResponse(
            toResponse(user),
            newAccessToken,
            newRefreshToken
        );
    }

    private void revokeCurrentActiveTokens(User user) {
        List<Token> currentActiveTokens = tokenRepository.findAllByUserAndTypeInAndRevokedFalseAndExpiredFalse(
            user,
            List.of(TokenType.ACCESS_TOKEN, TokenType.REFRESH_TOKEN)
        );

        if (!currentActiveTokens.isEmpty()) {
            currentActiveTokens.forEach(currentToken -> currentToken.setRevoked(true));
            tokenRepository.saveAll(currentActiveTokens);
        }
    }

    private void saveNewTokens(User user, String newAccessToken, String newRefreshToken) {
        Token accessToken = toToken(user, newAccessToken, TokenType.ACCESS_TOKEN);
        Token refreshToken = toToken(user, newRefreshToken, TokenType.REFRESH_TOKEN);

        tokenRepository.saveAll(List.of(accessToken, refreshToken));
    }
}
