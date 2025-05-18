package com.mongia.discpline.ledger.Exceptions;

import com.mongia.discpline.ledger.CommonUtils.Response.LedgerResponse;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

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

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<?> handleResourceNotFoundException(NoHandlerFoundException e){
        return new ResponseEntity<>(new LedgerResponse<String>(false,"Failed to find", e.getMessage()),HttpStatus.NOT_FOUND);
    }
}
