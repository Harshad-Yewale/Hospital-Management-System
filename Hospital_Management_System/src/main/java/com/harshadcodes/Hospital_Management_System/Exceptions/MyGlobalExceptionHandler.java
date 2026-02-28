package com.harshadcodes.Hospital_Management_System.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class MyGlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> myValidationErrorResponse(MethodArgumentNotValidException exception){
        List<FieldErrorResponse> errors= exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError ->
                        new FieldErrorResponse(
                                fieldError.getField(),
                                fieldError.getDefaultMessage()))
                .toList();
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ValidationErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Validation Failed",
                errors
        ));
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> myResourceNotFoundException (ResourceNotFoundException exception,HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new  ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "NotFound",
                exception.getMessage(),
                request.getRequestURI()
        ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> myGenericException(Exception ex, HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Internal Server Error",
                        "Something went wrong",
                        request.getRequestURI()
                ));
    }
  @ExceptionHandler(ResourceNotValidException.class)
    public ResponseEntity<ErrorResponse> myResourceNotValidException(ResourceNotValidException ex, HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST.value(),
                       "Not Valid",
                        ex.getMessage(),
                        request.getRequestURI()
                ));
    }

    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> myResourceAlreadyExistException (ResourceAlreadyExistException ex,HttpServletRequest request){

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.CONFLICT.value(),
                        "Already Exists",
                        ex.getMessage(),
                        request.getRequestURI()
                ));
    }

}
