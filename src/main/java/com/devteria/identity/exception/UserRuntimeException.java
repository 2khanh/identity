package com.devteria.identity.exception;

import com.devteria.identity.base.constants.HttpStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRuntimeException extends RuntimeException {

    HttpStatus httpStatus;

}
