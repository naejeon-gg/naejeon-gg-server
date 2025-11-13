package com.chlwkddn.scrim_com.global.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse {

    private final LocalDateTime timestamp;
    private final int status;
    private final String message;
    private final String path;

    public ErrorResponse(int status, String message, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
        this.path = path;
    }
}