package com.linkedin.linkedin.features.authentication.service;

import com.linkedin.linkedin.features.authentication.dto.AuthenticationUserRequest;
import com.linkedin.linkedin.features.authentication.dto.AuthenticationUserResponse;
import com.linkedin.linkedin.features.authentication.enums.Status;
import com.linkedin.linkedin.features.authentication.model.AuthenticationUser;
import com.linkedin.linkedin.features.authentication.repository.AuthenticationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.linkedin.linkedin.features.authentication.mapper.AuthenticationUserMapper.toEntity;
import static com.linkedin.linkedin.features.authentication.mapper.AuthenticationUserMapper.toResponse;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationRepository authenticationRepository;

    public AuthenticationUserResponse register(AuthenticationUserRequest authenticationUser) {
        if (authenticationRepository.existsByEmailAndStatus(authenticationUser.email(), Status.ACTIVE)) {
            throw new RuntimeException(String.format("User with email '%s' already exists", authenticationUser.email()));
        }

        AuthenticationUser entity = toEntity(authenticationUser);
        return toResponse(authenticationRepository.save(entity));
    }
}
