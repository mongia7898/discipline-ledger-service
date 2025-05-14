package com.mongia.discpline.ledger.Exceptions;

import com.mongia.discpline.ledger.CommonUtils.Response.LedgerResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> errorMessages = e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> {
                    String fieldName;
                    try {
                        fieldName = ((FieldError) error).getField();
                    } catch (ClassCastException ex) {
                        fieldName = error.getObjectName();
                    }
                    return String.format("%s: %s", fieldName, error.getDefaultMessage());
                })
                .collect(Collectors.toList());

        return new ResponseEntity<>(new LedgerResponse<List<String>>(false,"Failed",errorMessages), HttpStatus.BAD_REQUEST);
    }
}
