package com.workflex.workation.response;

import com.workflex.workation.constant.ApiMessages;
import org.springframework.http.HttpStatus;

/**
 * Standard envelope for every API response so success and error payloads share
 * one predictable shape on the wire, with the HTTP status mirrored in the body.
 */
public record ApiResponse<T>(int status, String message, T data) {

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(HttpStatus.OK.value(), ApiMessages.OK, data);
    }

    public static <T> ApiResponse<T> created(T data) {
        return new ApiResponse<>(HttpStatus.CREATED.value(), ApiMessages.CREATED, data);
    }

    public static <T> ApiResponse<T> error(int status, String message) {
        return new ApiResponse<>(status, message, null);
    }
}
