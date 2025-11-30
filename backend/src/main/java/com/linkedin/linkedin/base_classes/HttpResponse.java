package com.linkedin.linkedin.base_classes;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.linkedin.linkedin.enums.ResponseStatus;
import lombok.*;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@Builder
@JsonInclude(NON_NULL)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class HttpResponse {
    private Integer statusCode;
    private String description;
    private Object data;
    private ResponseStatus responseStatus;
}
