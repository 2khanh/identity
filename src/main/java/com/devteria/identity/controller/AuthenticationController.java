package com.devteria.identity.controller;

import com.devteria.identity.base.BaseApiResponse;
import com.devteria.identity.base.constants.HttpStatus;
import com.devteria.identity.dto.request.AuthenticationRequest;
import com.devteria.identity.dto.request.TokenVerifyRequest;
import com.devteria.identity.dto.response.AuthenticationResponse;
import com.devteria.identity.dto.response.TokenVerifyResponse;
import com.devteria.identity.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

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


        return new BaseApiResponse<AuthenticationResponse>(
                httpStatus.getCode(),
                httpStatus.getMessage(),
                isAuthenticated
        );
    }

    @PostMapping(path = "/verify")
    public BaseApiResponse<TokenVerifyResponse> doVerifyToken(
            @RequestBody TokenVerifyRequest request
    ) throws ParseException, JOSEException {
        TokenVerifyResponse tokenVerify = service.verifyToken(request);
        HttpStatus httpStatus = HttpStatus.VALID_TOKEN;

        return new BaseApiResponse<TokenVerifyResponse>(
                httpStatus.getCode(),
                httpStatus.getMessage(),
                tokenVerify
        );
    }

}
