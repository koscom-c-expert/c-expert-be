package com.koscom.cexpert.config;

import com.koscom.cexpert.dto.ApiResponse;
import com.koscom.cexpert.exception.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ApiResponse<Object>> handleCommonException(CommonException ex) {
        log.error(ex.getMessage());
        return ResponseEntity
                .status(ex.status)
                .body(ApiResponse.failure(ex.message));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleException(Exception ex) {
        log.error(ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.failure(ex.getMessage()));
    }
}
