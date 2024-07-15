package com.devteria.identity.base.constants;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum HttpStatus {

    // Auth
    SUCCEED_LOGIN(20000, "Authenticated. Login Successfully!"),
    FAILED_LOGIN(40000, "Not Authenticated. Login Failed!"),
    WRONG_PASSWORD(40000, "Please check again. Password is incorrect!"),

    // Others
    OUT_OF_EXCEPTION(49999, "Out of Exception Definition!"),

    // Validate
    INVALID_USERNAME_FIELD_SIZE(40000, "Username must be 4 characters minimum!"),
    INVALID_PASSWORD_FIELD_SIZE(40000, "Password must be 8 characters minimum!"),
    INVALID_EMAIL_FIELD_FORMAT(40000, "Email must have follow pattern: email.example@domain.com!"),
    INVALID_PHONENUMBER_FIELD_SIZE(40000, "Phone number must be between 10 to 20 characters!"),
    INVALID_FIRSTNAME_FIELD_SIZE(40000, "First name must be 2 characters minimum!"),
    INVALID_LASTNAME_FIELD_SIZE(40000, "Last name must be 2 characters minimum!"),

    // Data Status: Success
    SUCCEED_GET_USER(20000, "Get User Successfully!"),
    SUCCEED_GET_USER_LISTS(20000, "Get User Lists Successfully!"),
    SUCCEED_CREATE_USER(20000, "Create User Successfully!"),
    SUCCEED_UPDATE_USER(20000, "Update User Successfully!"),
    SUCCEED_DELETE_USER(20000, "Delete User Successfully!"),

    // Data Status: Error
    EXISTS_USER(44000, "User has been exists!"),
    EXISTS_USERNAME(44000, "Username has been exists!"),
    EXISTS_EMAIL(44000, "Email has been exists!"),
    EXISTS_PHONENUMBER(44000, "Phone number has been exists!"),
    NOT_FOUND_USER(44000, "User Not Found!"),
    NOT_FOUND_USER_LISTS(44000, "User Lists Not Found!")
    ;

    int code;
    String message;

}
