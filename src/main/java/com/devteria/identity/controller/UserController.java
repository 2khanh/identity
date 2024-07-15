package com.devteria.identity.controller;

import com.devteria.identity.base.BaseApiResponse;
import com.devteria.identity.base.constants.HttpStatus;
import com.devteria.identity.dto.request.UserRequest;
import com.devteria.identity.dto.response.UserResponse;
import com.devteria.identity.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserController {

    UserService service;

    @GetMapping(path = "/{uuid}")
    public BaseApiResponse<UserResponse> getUserById(
            @PathVariable String uuid
    ) {
        UserResponse userResponse = service.getUserById(uuid);
        HttpStatus httpStatus = HttpStatus.SUCCEED_GET_USER;

        return new BaseApiResponse<>(
                httpStatus.getCode(),
                httpStatus.getMessage(),
                userResponse
        );
    }

    @GetMapping(path = "/list")
    public BaseApiResponse<List<UserResponse>> getUserLists() {
        List<UserResponse> userResponses = service.getUserLists();
        HttpStatus httpStatus = HttpStatus.SUCCEED_GET_USER_LISTS;

        return new BaseApiResponse<>(
                httpStatus.getCode(),
                httpStatus.getMessage(),
                userResponses
        );
    }

    @PostMapping(path = "/add")
    public BaseApiResponse<UserResponse> createUser(
            @RequestBody @Valid UserRequest request
    ) {
        UserResponse userResponse = service.createUser(request);
        HttpStatus httpStatus = HttpStatus.SUCCEED_CREATE_USER;

        return new BaseApiResponse<>(
                httpStatus.getCode(),
                httpStatus.getMessage(),
                userResponse
        );
    }

    @PutMapping(path = "/edit/{uuid}")
    public BaseApiResponse<UserResponse> updateUser(
            @PathVariable String uuid,
            @RequestBody @Valid UserRequest request
    ) {
        UserResponse userResponse = service.updateUserById(uuid, request);
        HttpStatus httpStatus = HttpStatus.SUCCEED_UPDATE_USER;

        return new BaseApiResponse<>(
                httpStatus.getCode(),
                httpStatus.getMessage(),
                userResponse
        );
    }

    @DeleteMapping(path = "/delete/{uuid}")
    public BaseApiResponse<UserResponse> deleteUserById(
            @PathVariable String uuid
    ) {
        UserResponse userResponse = service.deleteUserById(uuid);
        HttpStatus httpStatus = HttpStatus.SUCCEED_DELETE_USER;

        return new BaseApiResponse<>(
                httpStatus.getCode(),
                httpStatus.getMessage(),
                userResponse
        );
    }

}
