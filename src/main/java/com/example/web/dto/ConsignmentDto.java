package com.example.web.dto;

import com.example.exception.CommonExceptionTypes;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

public class ConsignmentDto {
    // TODO - 추후 입력값 검증 필드 추가 필요
    @ToString
    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Request {
        private String title;
        private String isbn;
        private Integer price;
        private Integer userId;
        @Getter(AccessLevel.NONE)
        private boolean isValid = false;

        // TODO 세션 기능 구현
        public void initUserId(Integer userId) {
            this.isValid = true;
            this.userId = userId;
        }

        public void validate() {
            if(!this.isValid) throw CommonExceptionTypes.DEBUGGING_NEED_ERROR.toException();
        }
    }
}
