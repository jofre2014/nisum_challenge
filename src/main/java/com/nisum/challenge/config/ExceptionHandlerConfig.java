package com.nisum.challenge.config;

import com.nisum.challenge.dto.ErrorDto;
import com.nisum.challenge.exception.UserEmailAlreadyExistException;
import com.nisum.challenge.exception.UserNotFundException;
import com.nisum.challenge.exception.UserServerException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
@RestControllerAdvice
public class ExceptionHandlerConfig {

    @ExceptionHandler(value = UserEmailAlreadyExistException.class)
    public ResponseEntity<ErrorDto> userEmailAlreadyExistException(UserEmailAlreadyExistException exception){
        return generateBody(exception, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = UserNotFundException.class)
    public ResponseEntity<ErrorDto> userNotFundException(UserNotFundException exception){
        return generateBody(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = UserServerException.class)
    public ResponseEntity<ErrorDto> userServerException(UserServerException exception){
        return generateBody(exception,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<ErrorDto> handleException(ConstraintViolationException ex, WebRequest request) {

        return generateBody(ex, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> methodArgumentNotValidException(MethodArgumentNotValidException exception){
        List<String> errors = exception.getBindingResult()
                .getAllErrors().stream()
                .map(error -> {
                    if (error instanceof FieldError fieldError) {
                        return fieldError.getField() + ": " + fieldError.getDefaultMessage();
                    } else {
                        return error.getDefaultMessage();
                    }
                })
                .toList();
        String message = String.join(", ", errors);
        return generateBody(message, HttpStatus.BAD_REQUEST);
    }



    private ResponseEntity<ErrorDto> generateBody(Exception exception, HttpStatus statusCode) {

        return new ResponseEntity<>(
                ErrorDto.builder()
                        .mensaje(exception.getMessage())
                        .build(),
                statusCode

        );
    }

    private ResponseEntity<ErrorDto> generateBody(String message, HttpStatus statusCode) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMensaje(message);
        return new ResponseEntity<>(errorDto, statusCode);
    }
}