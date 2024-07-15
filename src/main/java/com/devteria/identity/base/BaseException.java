package com.devteria.identity.base;

import com.devteria.identity.base.constants.HttpStatus;
import com.devteria.identity.exception.UserRuntimeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BaseException {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<BaseApiResponse> handlingFieldValidationException(
            MethodArgumentNotValidException exception
    ) {
        String httpStatusEnum = exception.getFieldError().getDefaultMessage();

        HttpStatus httpStatus = HttpStatus.OUT_OF_EXCEPTION;
        try {
            httpStatus = HttpStatus.valueOf(httpStatusEnum);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }

        BaseApiResponse apiResponse = new BaseApiResponse<>();

        apiResponse.setCode(httpStatus.getCode());
        apiResponse.setMessage(httpStatus.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = UserRuntimeException.class)
    public ResponseEntity<BaseApiResponse> handlingUserRuntimeException(UserRuntimeException exception) {
        HttpStatus httpStatus = exception.getHttpStatus();
        BaseApiResponse apiResponse = new BaseApiResponse<>();

        apiResponse.setCode(httpStatus.getCode());
        apiResponse.setMessage(httpStatus.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }

}
