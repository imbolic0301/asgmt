package com.partimestudy.domain.user.service;

import com.partimestudy.domain.user.persistence.entity.UserEntity;
import com.partimestudy.domain.user.persistence.entity.UserTokenEntity;
import com.partimestudy.domain.user.persistence.mapper.UserMapper;
import com.partimestudy.domain.user.persistence.mapper.UserTokenMapper;
import com.partimestudy.domain.user.web.dto.UserSignDto;
import com.partimestudy.global.jwt.JwtProvider;
import com.partimestudy.global.jwt.RefreshToken;
import com.partimestudy.global.jwt.SessionInfo;
import com.partimestudy.global.jwt.SessionJwt;
import com.partimestudy.global.web.exception.CustomException;
import com.partimestudy.global.web.exception.ServiceExceptionTypes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserTokenService {

    private final JwtProvider jwtProvider;
    private final UserTokenMapper userTokenMapper;
    private final UserMapper userMapper;


    // 해당 액세스 토큰이 삭제되지 않았는지 확인
    @Transactional(readOnly = true)
    public void validateAliveAccessToken(SessionInfo sessionInfo) throws CustomException {
        // TODO 고도화 - 추후 accessToken 의 위치를 RDB 가 아닌 Redis 등으로 옮긴다
        Boolean isValid = userTokenMapper.isAliveAccessToken(sessionInfo.getUserId(), sessionInfo.getUuid());
        if(isValid == null || !isValid) throw ServiceExceptionTypes.INVALID_TOKEN.toException();
    }

    // 새로운 세션용 JWT 정보 생성
    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
    public SessionJwt createNewSessionJwt(UserEntity entity) {
        return sessionJwtFrom(entity);
    }

    // refreshToken 의 세션용 JWT 생성 및 기존 토큰 만료 처리
    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SessionJwt refreshSessionJwt(UserSignDto.TokenRefreshRequest request) throws CustomException {
        String jws = request.getRefreshToken();
        RefreshToken refreshToken = jwtProvider.refreshTokenFrom(jws);
        if(!Boolean.TRUE.equals(userTokenMapper.isAliveRefreshToken(refreshToken))) {
            throw ServiceExceptionTypes.EXPIRED_TOKEN.toException();
        }
        UserEntity existUser = userMapper.findUserById(refreshToken.getUserId());
        if(existUser == null) throw ServiceExceptionTypes.INVALID_TOKEN.toException();
        userTokenMapper.removeUsedToken(refreshToken);
        return sessionJwtFrom(existUser);
    }
    
    // sessionJwt 생성용 내부 함수 (Transactional 어노테이션 내부 호출 방지 용도로 분리)
    private SessionJwt sessionJwtFrom(UserEntity entity) {
        long createdMillis = System.currentTimeMillis();
        SessionInfo sessionInfo = SessionInfo.from(entity);
        RefreshToken refreshToken = RefreshToken.from(entity);
        SessionJwt sessionJwt = jwtProvider.sessionJwtFrom(createdMillis, sessionInfo, refreshToken);
        UserTokenEntity newToken = UserTokenEntity.from(entity, sessionInfo, refreshToken, sessionJwt);
        userTokenMapper.create(newToken);
        return sessionJwt;
    }

}
