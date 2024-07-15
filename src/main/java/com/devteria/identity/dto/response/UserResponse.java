package com.devteria.identity.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    String uuid;

    String username;

    String password;

    String email;

    String phoneNumber;

    String firstName;

    String lastName;

    LocalDate birthday;

}
