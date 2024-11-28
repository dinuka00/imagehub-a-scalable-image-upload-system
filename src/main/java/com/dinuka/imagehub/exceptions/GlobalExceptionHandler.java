package com.dinuka.imagehub.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ProblemDetail handleRuntimeException(RuntimeException e) {
        e.printStackTrace();
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


