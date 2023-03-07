package com.example.testovoetask.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum CommonErrorCode {
    ENTITY_NOT_FOUND("Entity is not found", HttpStatus.NOT_FOUND),
    INTERNAL_ERROR("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String message;

    private final HttpStatus httpStatus;
}
