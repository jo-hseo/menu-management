package org.menuservice.api.domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.menuservice.api.common.api.Api;
import org.menuservice.api.domain.user.model.UserLoginRequest;
import org.menuservice.api.domain.token.model.TokenResponse;
import org.menuservice.api.domain.user.business.UserBusiness;
import org.menuservice.api.domain.user.model.UserRegisterRequest;
import org.menuservice.api.domain.user.model.UserResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/open-api/user")
public class UserOpenApiController {

    private final UserBusiness userBusiness;

    @PostMapping("/register")
    @ResponseBody
    public Api<TokenResponse> register(
            @Valid
            @RequestBody
            Api<UserRegisterRequest> request
    ) {
        var response = userBusiness.register(request.getBody());
        return Api.OK(response);
    }

    @PostMapping("/login")
    @ResponseBody
    public Api<TokenResponse> login(
            @Valid
            @RequestBody
            Api<UserLoginRequest> request
    ) {
        var response = userBusiness.login(request.getBody());
        return Api.OK(response);
    }

    @GetMapping("/loginPage")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/registerPage")
    public String registerPage() {
        return "register";
    }
}
