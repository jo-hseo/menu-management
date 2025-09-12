package org.menuservice.api.domain.token.business;

import lombok.RequiredArgsConstructor;
import org.menuservice.api.common.error.ErrorCode;
import org.menuservice.api.common.exception.ApiException;
import org.menuservice.api.domain.token.converter.TokenConverter;
import org.menuservice.api.domain.token.model.TokenDto;
import org.menuservice.api.domain.token.model.TokenResponse;
import org.menuservice.api.domain.token.service.TokenService;
import org.menuservice.db.user.UserEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TokenBusiness {

    private final TokenService tokenService;

    private final TokenConverter tokenConverter;

    public TokenResponse issueToken(UserEntity userEntity) {

        return Optional.ofNullable(userEntity)
                .map(ue -> {
                    return ue.getId();
                })
                .map(userId -> {
                    var accessToken = tokenService.issueAccessToken(userId);
                    var refreshToken = tokenService.issueRefreshToken(userId);
                    return tokenConverter.toTokenResponse(accessToken, refreshToken);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }
}
