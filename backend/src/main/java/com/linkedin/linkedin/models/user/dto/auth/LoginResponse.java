package com.linkedin.linkedin.models.user.dto.auth;

import com.linkedin.linkedin.models.user.dto.UserResponse;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private UserResponse user;
    private String accessToken;
    private String refreshToken;
}
