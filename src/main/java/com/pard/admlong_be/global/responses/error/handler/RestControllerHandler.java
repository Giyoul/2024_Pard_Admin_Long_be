package com.pard.admlong_be.global.responses.error.handler;

import com.pard.pard_backend.global.responses.errors.ErrorResponse;
import com.pard.pard_backend.global.responses.errors.exceptions.ProjectException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class RestControllerHandler extends ResponseEntityExceptionHandler {

//    private final SlackErrorLogger slackErrorLogger;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex, HttpServletRequest request) {
        // 예외 로그 찍기
//        slackErrorLogger.sendSlackAlertErrorLog(ex.getMessage() + " 공습경보!!",request );
        return new ResponseEntity<>("공습경보 공습경보!!", HttpStatus.INTERNAL_SERVER_ERROR);
    }



    @ExceptionHandler(ProjectException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(final @NotNull ProjectException e,
                                                                 final @NotNull HttpServletRequest request) {

        log.error("Handle ['business exception'] , message: '{}'", e.getErrorCode().getMessage());
//        slackErrorLogger.sendSlackAlertErrorLog(e.getMessage(),request );
        return ErrorResponse.toResponseEntity(e.getErrorCode(), request.getRequestURI());
    }

    @ExceptionHandler(ProjectException.UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(ProjectException.UserNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(NullPointerException ex) {
        String errorMessage = "A null pointer exception occurred. Please check your request and try again.";
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalAccessError.class)
    public ResponseEntity<String> illegalAccessError(IllegalAccessError   ex) {
        String errorMessage = "A null pointer exception occurred. Please check your request and try again.";
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<String> handleSQLIntegrityConstraintViolationException(HttpServletRequest request) {
        // 예외 메시지를 사용자에게 반환
        String errorMessage = "이미 출석 체크 된 세미나 입니다.";
//        slackErrorLogger.sendSlackAlertErrorLog(errorMessage,request );
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
}
