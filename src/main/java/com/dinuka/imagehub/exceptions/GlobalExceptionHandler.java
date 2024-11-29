package com.dinuka.imagehub.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ProblemDetail handleUserNotFoundException(UserNotFoundException e) {
        return createProblemDetail(HttpStatus.NOT_FOUND, e.getMessage(), "An user was not found.");
    }

    @ExceptionHandler(RuntimeException.class)
    public ProblemDetail handleRuntimeException(RuntimeException e) {
        log.error("Runtime exception occurred", e);
        return createProblemDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), "A runtime exception occurred");
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleException(Exception e) {
        e.printStackTrace();
        return createProblemDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), "An unexpected internal server error occurred.");
    }



    private ProblemDetail createProblemDetail(HttpStatus status, String message, String description) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, message);
        problemDetail.setProperty("description", description);
        return problemDetail;
    }

}


