package com.partimestudy.domain.user.web.controller;

import com.partimestudy.domain.user.service.UserInfoService;
import com.partimestudy.domain.user.web.dto.UserDto;
import com.partimestudy.global.annotation.ResolvedParam;
import com.partimestudy.global.jwt.SessionInfo;
import com.partimestudy.global.web.dto.CommonDto;
import com.partimestudy.swagger.user.UserSwaggerDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "회원 정보", description = "회원 정보 관련 API 입니다.")
public class UserInfoController {

    private final UserInfoService userInfoService;


    @Operation(summary = "자기 정보 조회", description = "현재 회원 자신의 정보를 조회하는 API 입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = UserSwaggerDto.UserInfoExample.class)))
    })
    @GetMapping("/up")
    public ResponseEntity<?> getMyInfo(@ResolvedParam SessionInfo sessionInfo) {
        // 회원의 잔액 등을 서버에서 실시간으로 조회해야 하므로, JWT 가 아닌 DB 에서 데이터를 실시간으로 가져온다.
        UserDto.MyInfoResponse response = userInfoService.findMyInfoBy(sessionInfo);
        return CommonDto.responseFrom(response);
    }
    
    // TODO 고도화 - 회원 정보 수정 등, 회원 정보 관련 API 추가 필요

}
