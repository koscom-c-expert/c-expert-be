package com.koscom.cexpert.exception;

import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends CommonException {

    public EntityNotFoundException() {
        super(HttpStatus.NOT_FOUND, "대상을 찾을 수 없습니다.");
    }

    public EntityNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
