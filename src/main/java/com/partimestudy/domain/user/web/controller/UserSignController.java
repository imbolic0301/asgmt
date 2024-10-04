package com.partimestudy.domain.user.web.controller;


import com.partimestudy.domain.user.service.UserSignService;
import com.partimestudy.domain.user.service.UserTokenService;
import com.partimestudy.domain.user.web.dto.UserSignDto;
import com.partimestudy.swagger.session.SessionSwaggerDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "회원", description = "회원 관련 API 입니다.")
public class UserSignController {

    private final UserSignService signService;
    private final UserTokenService userTokenService;


    @Operation(summary = "회원 가입", description = "회원 가입을 처리하는 API 입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = SessionSwaggerDto.SessionJwtExample.class)))
    })
    @PostMapping
    public ResponseEntity<?> createNewUser(@RequestBody UserSignDto.SignUpRequest request) throws Exception {
        request.validate();
        var response = signService.signUpBy(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "회원 로그인", description = "회원 로그인을 처리하는 API 입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = SessionSwaggerDto.SessionJwtExample.class)))
    })
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody UserSignDto.SignInRequest request) throws Exception {
        request.validate();
        var response = signService.signInBy(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "토큰 재발급", description = "토큰 재발급을 처리하는 API 입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = SessionSwaggerDto.SessionJwtExample.class)))
    })
    @PostMapping("/auth/refresh")
    public ResponseEntity<?> loginByRefresh(@RequestBody UserSignDto.TokenRefreshRequest request) {
        request.validate();
        var response = userTokenService.refreshSessionJwt(request);
        return ResponseEntity.ok(response);
    }

}
