package com.linkedin.linkedin.models.user.service;

import com.linkedin.linkedin.enums.ResponseStatus;
import com.linkedin.linkedin.enums.Status;
import com.linkedin.linkedin.enums.TokenType;
import com.linkedin.linkedin.exception_handler.ApiException;
import com.linkedin.linkedin.models.token.entity.Token;
import com.linkedin.linkedin.models.token.repository.TokenRepository;
import com.linkedin.linkedin.models.user.entity.User;
import com.linkedin.linkedin.models.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.linkedin.linkedin.models.user.mapper.UserMapper.toUserPrincipal;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmailAndStatusNot(username, Status.DELETED)
            .orElseThrow(() -> new ApiException(ResponseStatus.USER_NOT_FOUND, username));

        if (user.getStatus() == Status.PENDING_VERIFICATION)
            throw new ApiException(ResponseStatus.USER_NOT_VERIFIED, username);

        Token accessToken = tokenRepository
            .findByUser_IdAndTypeAndRevokedFalseAndExpiredFalse(user.getId(), TokenType.ACCESS_TOKEN)
            .orElse(null);
        Token refreshToken = tokenRepository
            .findByUser_IdAndTypeAndRevokedFalseAndExpiredFalse(user.getId(), TokenType.REFRESH_TOKEN)
            .orElse(null);

        return toUserPrincipal(user, accessToken, refreshToken);
    }
}
