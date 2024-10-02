package com.partimestudy.global.web.Resolver;

import com.partimestudy.domain.user.service.UserTokenService;
import com.partimestudy.global.annotation.ResolvedParam;
import com.partimestudy.global.jwt.JwtProvider;
import com.partimestudy.global.jwt.SessionInfo;
import com.partimestudy.global.web.exception.CustomException;
import com.partimestudy.global.web.exception.ServiceExceptionTypes;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SessionArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtProvider jwtProvider;
    private final UserTokenService userTokenService;

    private static final String SESSION_HEADER_NAME = "Authorization";
    private static final String HEADER_PREFIX = "Bearer ";


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(ResolvedParam.class) && parameter.getParameterType().equals(SessionInfo.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        boolean isOptional = isOptionalSession(parameter);
        String headerValue = webRequest.getHeader(SESSION_HEADER_NAME);
        Optional<String> optionalJws = optionalJwtFromHeader(headerValue, isOptional);
        return optionalJws.map(this::sessionInfoFrom).orElse(null);
    }

    // 어노테이션을 통해 세션이 필수인지 확인한다.
    private boolean isOptionalSession(MethodParameter parameter) throws CustomException {
        ResolvedParam annotation = parameter.getParameterAnnotation(ResolvedParam.class);
        if(annotation == null) throw ServiceExceptionTypes.UNAUTHORIZED.toException();
        return annotation.isOptional();
    }

    // Authorization 헤더의 값을 받아 JWT 문자열을 리턴한다.
    private Optional<String> optionalJwtFromHeader(String headerValue, Boolean isOptional) throws CustomException {
        Optional<String> optionalJwt = Optional.empty();
        if(headerValue == null || headerValue.trim().isEmpty()) {
            if(isOptional) {
                return optionalJwt;
            } else {
                throw ServiceExceptionTypes.UNAUTHORIZED.toException();
            }
        }
        String[] splitValue = headerValue.split(HEADER_PREFIX);
        if(splitValue.length != 2) throw ServiceExceptionTypes.INVALID_TOKEN.toException();
        return Optional.of(splitValue[1]);
    }

    // jws 를 디코딩해서, redis 에서 활성화되어있는지 검증 후, 토큰 내에 있던 세션 정보를 리턴한다.
    private SessionInfo sessionInfoFrom(String jws) {
        SessionInfo sessionInfo = jwtProvider.sessionInfoFrom(jws);
        userTokenService.validateAliveAccessToken(sessionInfo);
        return sessionInfo;
    }

}
