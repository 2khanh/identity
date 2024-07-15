package com.devteria.identity.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    @Size(min = 4, message = "INVALID_USERNAME_FIELD_SIZE")
    String username;

    @Size(min = 8, message = "INVALID_PASSWORD_FIELD_SIZE")
    String password;

    @Email(message = "INVALID_EMAIL_FIELD_FORMAT")
    String email;

    @Size(min = 10, max = 20, message = "INVALID_PHONENUMBER_FIELD_SIZE")
    String phoneNumber;

    @Size(min = 2, message = "INVALID_FIRSTNAME_FIELD_SIZE")
    String firstName;

    @Size(min = 2, message = "INVALID_LASTNAME_FIELD_SIZE")
    String lastName;

    LocalDate birthday;

}
