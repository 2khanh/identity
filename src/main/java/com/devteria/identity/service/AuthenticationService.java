package com.devteria.identity.service;

import com.devteria.identity.base.constants.HttpStatus;
import com.devteria.identity.dto.request.AuthenticationRequest;
import com.devteria.identity.dto.request.TokenVerifyRequest;
import com.devteria.identity.dto.response.AuthenticationResponse;
import com.devteria.identity.dto.response.TokenVerifyResponse;
import com.devteria.identity.entity.User;
import com.devteria.identity.exception.UserRuntimeException;
import com.devteria.identity.repository.IUserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthenticationService {

    IUserRepository userRepo;

    @NonFinal
    @Value("${jwt.secret-key}")
    String JWT_SECRET_KEY;

    public AuthenticationResponse doAuthentication(AuthenticationRequest request) {
        User user = userRepo.findByUsername(request.getUsername());
        if (Objects.isNull(user)) {
            throw new UserRuntimeException(HttpStatus.NOT_FOUND_USER);
        }

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        boolean isPasswordCorrect = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        );
        if (!isPasswordCorrect) {
            throw new UserRuntimeException(HttpStatus.WRONG_PASSWORD);
        }

        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setAuthenticated(true);
        authenticationResponse.setApiToken(generateToken(request.getUsername()));

        return authenticationResponse;
    }

    private String generateToken(String username) {
        // 01. Create Header section
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        // 02. Create Payload section
        Date expirationTime = new Date(Instant.now()
                .plus(1, ChronoUnit.HOURS)
                .toEpochMilli());
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("devteria.com")
                .issueTime(new Date())
                .expirationTime(expirationTime)
                .build();
        Payload payload = new Payload(claimsSet.toJSONObject());

        // 03. Create Token
        JWSObject jwtToken = new JWSObject(header, payload);

        // 04. Token Signature
        try {
            jwtToken.sign(new MACSigner(JWT_SECRET_KEY.getBytes()));
            return jwtToken.serialize();
        } catch (RuntimeException | JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    public TokenVerifyResponse verifyToken(
            TokenVerifyRequest request
    ) throws JOSEException, ParseException
    {
        String token = request.getToken();

        JWSVerifier verifier = new MACVerifier(JWT_SECRET_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        boolean isTokenVerify = signedJWT.verify(verifier);
        boolean isTokenInUse = expirationTime.after(new Date());

        if (!isTokenVerify) {
            throw new UserRuntimeException(HttpStatus.INVALID_TOKEN);
        }
        if (!isTokenInUse) {
            throw new UserRuntimeException(HttpStatus.EXPIRED_TOKEN);
        }

        return new TokenVerifyResponse(
                true,
                new Date(Instant.now().toEpochMilli())
        );

    }

}
