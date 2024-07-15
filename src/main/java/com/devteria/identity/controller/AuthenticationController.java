package com.devteria.identity.controller;

import com.devteria.identity.base.BaseApiResponse;
import com.devteria.identity.base.constants.HttpStatus;
import com.devteria.identity.dto.request.AuthenticationRequest;
import com.devteria.identity.dto.response.AuthenticationResponse;
import com.devteria.identity.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthenticationController {

    AuthenticationService service;

    @PostMapping("/login")
    public BaseApiResponse<AuthenticationResponse> doAuthentication(
            @RequestBody AuthenticationRequest request
    ) {
        AuthenticationResponse isAuthenticated = service.doAuthentication(request);
        HttpStatus httpStatus = HttpStatus.SUCCEED_LOGIN;


        return new BaseApiResponse<>(
                httpStatus.getCode(),
                httpStatus.getMessage(),
                isAuthenticated
        );
    }

}
