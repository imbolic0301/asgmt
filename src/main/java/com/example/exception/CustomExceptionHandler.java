package com.example.exception;

import com.example.web.dto.CommonDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;

/**
 * Exception 이 발생하면 오류에 관한 공용 응답으로 가공하는 전역 핸들러
 */
@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    /**
     * 공용 예외 목록
     */

    // 지정한 ResultCode 가 있는 예외 핸들링    
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomException(CustomException exception) {
        printAbout(exception);
        return CommonDto.exceptionResponseFrom(exception);
    }
    
    // 요청 URL 경로에 해당하는 라우터(컨트롤러의 매핑 경로)가 없는 경우
    @ExceptionHandler(value = NoHandlerFoundException.class)
    protected ResponseEntity<?> handleNotFoundPathException(NoHandlerFoundException exception) {
        printAbout(exception);
        return CommonDto.exceptionResponseFrom(CommonExceptionTypes.NOT_FOUND_API.toException());
    }

    // 잘못된 enum 유형 값 파라미터 입력시 (multipart), int 가 들어갈 수 있는 API 경로에 문자열이 들어간 경우 등 전반적인 파라미터 입력 오류시 발생
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleException(MethodArgumentTypeMismatchException exception) {
        printAbout(exception);
        return CommonDto.exceptionResponseFrom(CommonExceptionTypes.INVALID_REQUEST_PARAMETER.toException());
    }

    // 잘못된 Http Method 입력시
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> handleMissingServletRequestPartException(HttpRequestMethodNotSupportedException exception) {
        printAbout(exception);
        return CommonDto.exceptionResponseFrom(CommonExceptionTypes.NOT_FOUND_API.toException());
    }

    // 전역 예외 처리, 미리 확인하지 못한 디버깅 용도
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleBaseException(Exception exception) {
        printAbout(exception);
        return CommonDto.exceptionResponseFrom(CommonExceptionTypes.DEBUGGING_NEED_ERROR.toException());
    }

    private static void printAbout(Exception exception) {
        log.error("exception occurred at : {}", LocalDateTime.now());
        log.error(exception.getClass().getCanonicalName());
        log.error(exception.getMessage());
        exception.printStackTrace();
    }

}
