package com.address.exception;


import feign.RetryableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler extends com.commonlib.exception.GlobalExceptionHandler {

    @ExceptionHandler(RetryableException.class)
    public ResponseEntity<com.commonlib.exception.ErrorResponse> handleRetryableException(RetryableException ex) {
        com.commonlib.exception.ErrorResponse response = new com.commonlib.exception.ErrorResponse(
                "Employee Service is currently unavailable. Please try again later",
                HttpStatus.SERVICE_UNAVAILABLE
        );
        return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
    }

//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
//        ErrorResponse response = new ErrorResponse(
//                ex.getMessage(),
//                ex.getStatus()
//        );
//        return new ResponseEntity<>(response, ex.getStatus());
//    }
//
//    @ExceptionHandler(BadRequestException.class)
//    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex) {
//        ErrorResponse response = new ErrorResponse(
//                ex.getMessage(),
//                ex.getStatus()
//        );
//        return new ResponseEntity<>(response, ex.getStatus());
//    }
//    @ExceptionHandler(CustomException.class)
//    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
//
//        ErrorResponse errorResponse = new ErrorResponse(
//                ex.getMessage(),
//                ex.getStatus()
//        );
//
//        return ResponseEntity
//                .status(ex.getStatus())
//                .body(errorResponse);
//    }
//
//    @ExceptionHandler(MissingParameterException.class)
//    public ResponseEntity<ErrorResponse> handleMissingParameterException(MissingParameterException ex) {
//        ErrorResponse response = new ErrorResponse(
//                ex.getMessage(),
//                ex.getStatus()
//        );
//        return new ResponseEntity<>(response, ex.getStatus());
//    }
}

