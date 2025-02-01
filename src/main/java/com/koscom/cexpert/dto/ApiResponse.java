package com.koscom.cexpert.dto;

import lombok.Getter;

@Getter
public class ApiResponse<T> {
    private final String status;
    private final String message;
    private final T data;

    private static final String SUCCESS = "success";
    private static final String FAILURE = "failure";

    public ApiResponse(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> success(T data) {
        return success(null, data);
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(SUCCESS, message, data);
    }

    public static <T> ApiResponse<T> failure(String message) {
        return new ApiResponse<>(FAILURE, message, null);
    }
}
