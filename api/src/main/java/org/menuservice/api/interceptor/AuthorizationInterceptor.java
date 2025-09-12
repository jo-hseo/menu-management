package org.menuservice.api.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.menuservice.api.common.error.ErrorCode;
import org.menuservice.api.common.error.TokenErrorCode;
import org.menuservice.api.common.exception.ApiException;
import org.menuservice.api.domain.token.business.TokenBusiness;
import org.menuservice.api.domain.token.service.TokenService;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import java.util.Objects;


@Slf4j
@RequiredArgsConstructor
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    private final TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {    //handler : 가야 할 컨트롤러 정보
        log.info("Authorization Interceptor URL : {}", request.getRequestURI());

        if(HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }

        if(handler instanceof ResourceHttpRequestHandler) {
            return true;
        }


        var accessToken = request.getHeader("authorization-token");
        if(accessToken == null) {
            throw new ApiException(TokenErrorCode.AUTHORIZATION_TOKEN_NOT_FOUND);
        }

        var id = tokenService.validationToken(accessToken);

        if(id == null) {
            throw new ApiException(ErrorCode.BAD_REQUEST, "인증 실패");
        }

        //TODO 로그인 버튼 -> 로그아웃 버튼

        var requestContext = Objects.requireNonNull(RequestContextHolder.getRequestAttributes());
        requestContext.setAttribute("userId", id, RequestAttributes.SCOPE_REQUEST);
        return true;
    }
}
