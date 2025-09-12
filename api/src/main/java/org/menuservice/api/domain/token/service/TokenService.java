package org.menuservice.api.domain.token.service;

import lombok.RequiredArgsConstructor;
import org.menuservice.api.common.error.ErrorCode;
import org.menuservice.api.common.exception.ApiException;
import org.menuservice.api.domain.token.ifs.TokenHelperIfs;
import org.menuservice.api.domain.token.model.TokenDto;
import org.menuservice.api.domain.token.model.TokenResponse;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenHelperIfs tokenHelperIfs;

    public TokenDto issueAccessToken(Long userId) {
        var data = new HashMap<String, Object>();
        data.put("userId", userId);
        return tokenHelperIfs.issueAccessToken(data);
    }

    public TokenDto issueRefreshToken(Long userId) {
        var data = new HashMap<String, Object>();
        data.put("userId", userId);
        return tokenHelperIfs.issueeRefreshToken(data);
    }

    public Long validationToken(String token) {
        var map = tokenHelperIfs.validationTokenWithThrow(token);
        var userId = map.get("userId");
        Objects.requireNonNull(userId, () -> {throw new ApiException(ErrorCode.NULL_POINT);});
        return Long.parseLong(userId.toString());
    }
}
