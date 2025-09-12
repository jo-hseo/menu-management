package org.menuservice.api.domain.token.ifs;

import org.menuservice.api.domain.token.model.TokenDto;

import java.util.Map;

public interface TokenHelperIfs {

    TokenDto issueAccessToken(Map<String, Object> data);
    TokenDto issueeRefreshToken(Map<String, Object> data);
    Map<String, Object> validationTokenWithThrow(String token);
}
