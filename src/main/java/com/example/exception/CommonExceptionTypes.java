package com.example.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * API 호출에 대한 공용 오류 메세지의 코드와 설명을 관리한다.
 */
@Getter
@AllArgsConstructor
public enum CommonExceptionTypes {

    // 고정 코드
    SUCCESS(HttpStatus.OK, "0000", "성공"),
    DEBUGGING_NEED_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "9999", "미확인 오류"),
    // 전역 공유 코드 (1000 ~ )
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "1000", "존재하지 않는 정보입니다."),
    NOT_FOUND_API(HttpStatus.BAD_REQUEST, "1001", "찾을 수 없는 API 경로입니다."),
    INVALID_REQUEST_PARAMETER(HttpStatus.BAD_REQUEST, "1002", "API 요청 파라미터를 확인해주세요."),
    ;

    private final HttpStatus resultStatus;
    private final String resultCode;
    private final String resultMessage;

    public CustomException toException() {
        return CustomException.builder()
                .resultStatus(this.resultStatus)
                .errorCode(this.resultCode)
                .errorMessage(this.resultMessage)
                .build();
    }

    // 프론트에서 오류 메세지를 그대로 alert 에 쓸 수 있게 커스텀 오류 메세지를 지정해준다.
    public static CustomException initCustomExceptionWith(String customErrorMessage) {
        return CustomException.builder()
                .resultStatus(HttpStatus.BAD_REQUEST)
                .errorCode("9998")
                .errorMessage(customErrorMessage)
                .build();
    }

}