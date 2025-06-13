package com.cryptotrading.cryptotrading.util;

import com.cryptotrading.cryptotrading.domain.dto.response.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto> handleAllExceptions(Exception ex) {

        ResponseDto result = new ResponseDto();
        result.setErrorMessage("An unexpected error occurred!");
        result.setStatus(false);
        logger.error("Unhandled exception caught: ", ex);
        return ResponseEntity.internalServerError().body(result);
    }
}