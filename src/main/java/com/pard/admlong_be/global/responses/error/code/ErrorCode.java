package com.pard.admlong_be.global.responses.error.code;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    String name();
    HttpStatus httpStatus();
    String Message();

}
