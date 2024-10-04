package com.partimestudy.global.web.exception;

import com.partimestudy.global.web.dto.CommonResponse;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

/**
 * 커스텀 예외 클래스
 */
@Getter
@ToString
public class CustomException extends RuntimeException {
    private final HttpStatus resultStatus;
    private final String errorCode;
    private final String errorMessage;
    private String externalError;

    public CustomException(CommonResponse response) {
        this.resultStatus = response.getResultStatus();
        this.errorCode = response.getResultCode();
        this.errorMessage = response.getResultMessage();
    }

    public CustomException(HttpStatus resultStatus, String errorCode, String errorMessage, String externalError) {
        this.resultStatus = resultStatus;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.externalError = externalError;
    }
}
