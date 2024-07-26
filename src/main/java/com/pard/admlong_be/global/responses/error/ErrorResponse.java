package com.pard.admlong_be.global.responses.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pard.admlong_be.global.responses.error.code.ErrorCode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private int status;

    private String error;

    private String message;

    private Map<String, String> errors;

    private String path;

    private ErrorResponse(final @NotNull ErrorCode errorCode,
                          final @NotBlank String requestURI) {
        this.status = errorCode.httpStatus().value();
        this.error = errorCode.httpStatus().getReasonPhrase();
        this.message = errorCode.Message();
        this.path = requestURI;
    }

    public static ResponseEntity<ErrorResponse> toResponseEntity(final @NotNull ErrorCode errorCode,
                                                                 final @NotBlank String requestURI) {
        return ResponseEntity
                .status(errorCode.httpStatus())
                .body(new ErrorResponse(errorCode, requestURI));
    }
}