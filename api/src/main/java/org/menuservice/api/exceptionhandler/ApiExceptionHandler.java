package org.menuservice.api.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.menuservice.api.common.api.Api;
import org.menuservice.api.common.error.ErrorCode;
import org.menuservice.api.common.exception.ApiException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order(Integer.MIN_VALUE )
public class ApiExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Api<Object>> apiException(
            ApiException apiException
    ) {
        log.error("", apiException);

        var errorCode = apiException.getErrorCodeIfs();

        //TODO 만기된 토큰이면 로그인 페이지로 이동

        return ResponseEntity
                .status(errorCode.getHttpStatusCode())
                .body(
                        Api.ERROR(errorCode, apiException.getErrorDescription())
                );
    }
}
