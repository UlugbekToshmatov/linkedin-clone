package com.linkedin.linkedin.features.authentication.controller;

import com.linkedin.linkedin.features.authentication.dto.AuthenticationUserRequest;
import com.linkedin.linkedin.features.authentication.dto.AuthenticationUserResponse;
import com.linkedin.linkedin.features.authentication.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/authentication")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @GetMapping("/")
    public ResponseEntity<String> getUser() {
        return ResponseEntity.ok("user");
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationUserResponse> register(
        @RequestBody @Valid AuthenticationUserRequest request
    ) {
        return ResponseEntity.ok(authenticationService.register(request));
    }
}
