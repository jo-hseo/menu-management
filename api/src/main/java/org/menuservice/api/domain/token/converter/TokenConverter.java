package org.menuservice.api.domain.token.converter;

import lombok.RequiredArgsConstructor;
import org.menuservice.api.common.error.ErrorCode;
import org.menuservice.api.common.exception.ApiException;
import org.menuservice.api.domain.token.model.TokenDto;
import org.menuservice.api.domain.token.model.TokenResponse;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class TokenConverter {

    public TokenResponse toTokenResponse(TokenDto accessToken, TokenDto refreshToken) {

        Objects.requireNonNull(accessToken, () -> {throw new ApiException(ErrorCode.NULL_POINT);});
        Objects.requireNonNull(refreshToken, () -> {throw new ApiException(ErrorCode.NULL_POINT);});

        return TokenResponse.builder()
                .accessToken(accessToken.getToken())
                .accessTokenExpiredAt(accessToken.getExpiredAt())
                .refreshToken(refreshToken.getToken())
                .refreshTokenExpiredAt(refreshToken.getExpiredAt())
                .build();
    }
}
