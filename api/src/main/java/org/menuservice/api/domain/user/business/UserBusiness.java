package org.menuservice.api.domain.user.business;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.menuservice.api.domain.user.model.UserLoginRequest;
import org.menuservice.api.domain.token.business.TokenBusiness;
import org.menuservice.api.domain.token.model.TokenResponse;
import org.menuservice.api.domain.user.converter.UserConverter;
import org.menuservice.api.domain.user.model.UserRegisterRequest;
import org.menuservice.api.domain.user.model.UserResponse;
import org.menuservice.api.domain.user.service.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserBusiness {

    private final UserService userService;
    private final UserConverter userConverter;
    private final TokenBusiness tokenBusiness;

    public TokenResponse register(@Valid UserRegisterRequest request) {
        var entity = userConverter.toEntity(request);
        var savedEntity = userService.register(entity);
        return tokenBusiness.issueToken(savedEntity);
    }

    public TokenResponse login(@Valid UserLoginRequest request) {
        var userEntity = userService.login(request.getUserId(), request.getPassword());
        return tokenBusiness.issueToken(userEntity);
    }
}
