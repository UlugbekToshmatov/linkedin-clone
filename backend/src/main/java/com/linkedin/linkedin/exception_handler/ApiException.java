package com.linkedin.linkedin.exception_handler;

import com.linkedin.linkedin.enums.ResponseStatus;
import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
    private final ResponseStatus responseStatus;
    private final Object[] params;

    public ApiException(ResponseStatus responseStatus, Object... params) {
        super(responseStatus.name());
        this.responseStatus = responseStatus;
        this.params = params;
    }

    public String getFormattedMessage() {
        return String.format(responseStatus.getDescription(), params);
    }
}
