package com.linkedin.linkedin.models.user.mapper;

import com.linkedin.linkedin.models.token.entity.Token;
import com.linkedin.linkedin.models.user.dto.UserPayload;
import com.linkedin.linkedin.models.user.dto.UserPrincipal;
import com.linkedin.linkedin.models.user.dto.auth.RegistrationRequest;
import com.linkedin.linkedin.models.user.dto.UserResponse;
import com.linkedin.linkedin.models.user.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserMapper {

    public static User toEntity(RegistrationRequest request, PasswordEncoder passwordEncoder) {
        User newUser = new User();
        BeanUtils.copyProperties(request, newUser, "email", "password");
        newUser.setEmail(request.email().trim().toLowerCase());
        newUser.setPassword(passwordEncoder.encode(request.password()));

        return newUser;
    }

    public static UserResponse toResponse(User user) {
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(user, userResponse);

        return userResponse;
    }

    public static UserPrincipal toUserPrincipal(User user, Token accessToken, Token refreshToken) {
        UserPayload userPayload = new UserPayload();
        BeanUtils.copyProperties(user, userPayload);
        userPayload.setAccessToken(accessToken != null ? accessToken.getJwtToken() : null);
        userPayload.setRefreshToken(refreshToken != null ? refreshToken.getJwtToken() : null);

        return new UserPrincipal(userPayload);
    }

}
