package com.partimestudy.swagger.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

public class CommonSwaggerDto {
    @ToString
    @Getter
    @Schema(description = "요청 성공시 빈 응답 리턴 공용 예시")
    public static class EmptyBodyExample {
    }
}
