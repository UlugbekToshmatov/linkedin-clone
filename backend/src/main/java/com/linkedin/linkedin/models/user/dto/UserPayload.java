package com.linkedin.linkedin.models.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.linkedin.linkedin.enums.Role;
import com.linkedin.linkedin.enums.Status;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserPayload {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
    private Status status;
    private String accessToken;
    private String refreshToken;
}
