package com.example.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.util.Map;

/**
 * 커스텀 예외 클래스
 */
@Getter
@ToString
@Builder
public class CustomException extends RuntimeException {
    private final HttpStatus resultStatus;
    private final String errorCode;
    private final String errorMessage;

    public CustomException(HttpStatus resultStatus, String errorCode, String errorMessage) {
        this.resultStatus = resultStatus;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
