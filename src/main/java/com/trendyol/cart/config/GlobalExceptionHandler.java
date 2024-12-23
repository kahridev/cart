package com.trendyol.cart.config;

import com.trendyol.cart.model.response.CommandResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CommandResult> handleIllegalArgumentException(IllegalArgumentException ex) {
        var result =
                CommandResult.builder().result(false).message(ex.getMessage()).build();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
