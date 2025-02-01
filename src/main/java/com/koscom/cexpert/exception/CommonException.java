package com.koscom.cexpert.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public class CommonException extends RuntimeException {
    public final HttpStatus status;
    public final String message;
}