package com.random.controller;


import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
@Log4j2
public class ExceptionController {

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity restClientException(HttpClientErrorException ex) {
        log.error("status code : {} , error message : {}" ,
                ex.getStatusCode().value(),ex.getMessage() );
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity restClientException(Exception ex) {
        log.error("error message : {}" , ex.getMessage(),ex);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
    }

}
