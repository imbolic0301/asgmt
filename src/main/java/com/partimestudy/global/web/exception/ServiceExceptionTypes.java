package com.partimestudy.global.web.exception;

import com.partimestudy.global.web.dto.CommonResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * API 호출에 대한 공용 오류 메세지의 코드와 설명을 관리한다.
 */
@Getter
@AllArgsConstructor
public enum ServiceExceptionTypes implements CommonResponse {

    // 고정 코드
    SUCCESS(HttpStatus.OK, "0000", "성공"),
    DEBUGGING_NEED_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "9999", "미확인 오류"),
    // 전역 공유 코드 (1000 ~ )
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "1000", "존재하지 않는 정보입니다."),
        // 1001 은 ParameterResultType.INVALID_PARAMETER_FAILURE_MESSAGE 에 사용
    INVALID_ENUM_PARAMETER(HttpStatus.BAD_REQUEST, "1002", "올바르지 못한 유형의 enum 값입니다."),
    MISS_REQUIRED_PARAMETER(HttpStatus.BAD_REQUEST, "1003", "필수 파라미터가 누락되었습니다."),
    NOT_FOUND_API(HttpStatus.BAD_REQUEST, "1004", "찾을 수 없는 API 경로입니다."),
    DUPLICATED_REQUEST(HttpStatus.BAD_REQUEST, "1005", "중복된 요청입니다."),
        // 1005 : ParameterResultType.INVALID_JSON_FAILURE_MESSAGE 에 사용
    INVALID_REQUEST_PARAMETER(HttpStatus.BAD_REQUEST, "1006", "올바르지 않은 API 요청입니다."),
    SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, "1007", "처리 도중 문제가 발생하였습니다. 재시도 해주세요."),
    // 세션 관련 (1100 ~)
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "1100", "인증되지 않은 사용자입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "1101", "세션이 만료된 사용자입니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "1102", "올바르지 못한 인증입니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "1103", "접근 권한이 없는 사용자입니다."),
    NOT_VERIFIED_USER(HttpStatus.UNAUTHORIZED, "1104", "본인 인증이 필요한 회원입니다."),
    // 유저 관련 (1200 ~ )
    NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "1201", "찾을 수 없는 회원입니다."),
    DUPLICATED_EMAIL_USER(HttpStatus.BAD_REQUEST, "1202", "중복된 로그인 아이디입니다."),
    // 포인트 관련 (1300 ~ )
    LACK_OF_AVAILABLE_POINTS(HttpStatus.BAD_REQUEST, "1301", "잔고가 부족합니다."),

    // 외부 API 오류 코드 (6000 ~ )
    EMPTY_RESPONSE(HttpStatus.SERVICE_UNAVAILABLE, "6001", "외부 API 호출의 응답을 찾을 수 없습니다."),
    API_RETURN_EXCEPTION(HttpStatus.SERVICE_UNAVAILABLE, "6002", "외부 API 호출 과정에서 오류가 발생하였습니다."),
    EXTERNAL_API_FAILED_TO_CALL(HttpStatus.SERVICE_UNAVAILABLE, "6002", "외부 API 호출 과정에서 오류가 발생하였습니다."),
    ;

    private final HttpStatus resultStatus;
    private final String resultCode;
    private final String resultMessage;



}