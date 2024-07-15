package com.devteria.identity.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "uuid")
    String uuid;

    @Column(name = "username", length = 64, unique = true, nullable = false)
    String username;

    @Column(name = "password", nullable = false)
    String password;

    @Column(name = "email", unique = true, nullable = false)
    String email;

    @Column(name = "phone_number", length = 20, unique = true, nullable = false)
    String phoneNumber;

    @Column(name = "first_name", length = 64)
    String firstName;

    @Column(name = "last_name", length = 64)
    String lastName;

    @Column(name = "birthday")
    LocalDate birthday;

}
