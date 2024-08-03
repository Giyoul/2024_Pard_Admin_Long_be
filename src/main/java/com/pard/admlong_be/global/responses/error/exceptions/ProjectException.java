package com.pard.admlong_be.global.responses.error.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProjectException extends RuntimeException {
    private final String errorCode;

    public static class UserNotFoundException extends ProjectException {
        public UserNotFoundException(String errorMessage) {super(errorMessage);}
    }

    public static class UserNotExistException extends ProjectException {
        public UserNotExistException(String errorMessage) {super(errorMessage);}
    }


    public static class UserFacadeException extends Exception {
        public UserFacadeException(String message) {
            super(message);
        }
        public UserFacadeException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class InvalidValueException extends ProjectException {
        public InvalidValueException(String errorMessage) {super(errorMessage);}
    }

}
