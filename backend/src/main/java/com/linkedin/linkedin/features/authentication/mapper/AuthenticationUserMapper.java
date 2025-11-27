package com.linkedin.linkedin.features.authentication.mapper;

import com.linkedin.linkedin.features.authentication.dto.AuthenticationUserRequest;
import com.linkedin.linkedin.features.authentication.dto.AuthenticationUserResponse;
import com.linkedin.linkedin.features.authentication.enums.Status;
import com.linkedin.linkedin.features.authentication.model.AuthenticationUser;
import com.linkedin.linkedin.features.authentication.utils.Encoder;
import org.springframework.beans.BeanUtils;

public class AuthenticationUserMapper {

    public static AuthenticationUser toEntity(AuthenticationUserRequest authenticationUserRequest) {
        AuthenticationUser authenticationUser = new AuthenticationUser();
        authenticationUser.setEmail(authenticationUserRequest.email());
        authenticationUser.setPassword(Encoder.encode(authenticationUserRequest.password()));
        authenticationUser.setStatus(Status.INACTIVE);

        return authenticationUser;
    }

    public static AuthenticationUserResponse toResponse(AuthenticationUser authenticationUser) {
        AuthenticationUserResponse authenticationUserResponse = new AuthenticationUserResponse();
        BeanUtils.copyProperties(authenticationUser, authenticationUserResponse);

        return authenticationUserResponse;
    }

}
