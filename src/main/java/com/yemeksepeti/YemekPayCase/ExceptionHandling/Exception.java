package com.yemeksepeti.YemekPayCase.ExceptionHandling;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class Exception {

    private Exception e;

    @ExceptionHandler
    public ResponseEntity<?> exceptionAll(Exception e){
        return ResponseEntity.ok(e.getMessage()+" Sorun var ");
    }
}
