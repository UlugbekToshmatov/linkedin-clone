package com.linkedin.linkedin.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseStatus {
    OK(0, HttpStatus.OK, "Success"),
    INTERNAL_SERVER_ERROR(1, HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"),
    REQUEST_PARAMETER_NOT_FOUND(2, HttpStatus.BAD_REQUEST, "Request parameter '%s' not found"),
    INVALID_REQUEST(3, HttpStatus.BAD_REQUEST, "Invalid request '%s'"),
    TOKEN_REQUIRED(4, HttpStatus.BAD_REQUEST, "Missing JWT token"),
    TOKEN_NOT_FOUND(5, HttpStatus.BAD_REQUEST, "Token not found"),
    INVALID_TOKEN(6, HttpStatus.BAD_REQUEST, "Invalid token"),
    TOKEN_EXPIRED(7, HttpStatus.BAD_REQUEST, "Token expired"),
    TOKEN_MISMATCH_EXCEPTION(8, HttpStatus.BAD_REQUEST, "This token does not belong to you! Use your own token."),
    TOKEN_PROCESSING_ERROR(9, HttpStatus.INTERNAL_SERVER_ERROR, "Token processing error"),
    TOKEN_MUST_START_WITH_BEARER(10, HttpStatus.BAD_REQUEST, "Token must start with 'Bearer '"),
    UNKNOWN_TYPE(11, HttpStatus.BAD_REQUEST, "Unknown type provided"),
    ERROR_GETTING_ACCESS_TOKEN(12, HttpStatus.INTERNAL_SERVER_ERROR, "Error while getting access token"),
    METHOD_NOT_ALLOWED(13, HttpStatus.BAD_REQUEST, "Method not allowed"),
    UNAUTHORIZED(14, HttpStatus.UNAUTHORIZED, "Authentication failed or token expired"),
    FORBIDDEN(15, HttpStatus.FORBIDDEN, "You do not have enough permissions to access this resource"),
    RESOURCE_NOT_FOUND(16, HttpStatus.NOT_FOUND, "Resource not found"),
    USER_NOT_FOUND(100, HttpStatus.NOT_FOUND, "User with email '%s' not found"),
    EMAIL_ALREADY_REGISTERED(101, HttpStatus.BAD_REQUEST, "Email '%s' is already registered"),
    USER_NOT_VERIFIED(102, HttpStatus.UNAUTHORIZED, "User with email '%s' not verified"),
    USER_BLOCKED(103, HttpStatus.FORBIDDEN, "Account blocked"),
    INCORRECT_PASSWORD(104, HttpStatus.BAD_REQUEST, "Incorrect password");


    private final Integer statusCode;
    private final HttpStatus httpStatus;
    private final String description;

    public String format(Object... args) {
        return String.format(description, args);
    }
}
