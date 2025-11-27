package com.linkedin.linkedin.features.authentication.dto;

import com.linkedin.linkedin.features.authentication.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthenticationUserResponse {
    private Long id;
    private String email;
    private Status status;
}
