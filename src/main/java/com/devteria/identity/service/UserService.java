package com.devteria.identity.service;

import com.devteria.identity.base.constants.HttpStatus;
import com.devteria.identity.dto.request.UserRequest;
import com.devteria.identity.dto.response.UserResponse;
import com.devteria.identity.entity.User;
import com.devteria.identity.exception.UserRuntimeException;
import com.devteria.identity.mapper.IUserMapper;
import com.devteria.identity.repository.IUserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserService {

    IUserRepository repo;
    IUserMapper mapper;

    public UserResponse getUserById(String uuid) {
        User user = repo.findById(uuid).orElseThrow(
                () -> new UserRuntimeException(HttpStatus.NOT_FOUND_USER)
        );

        return mapper.toUserResponse(user);
    }

    public List<UserResponse> getUserLists() {
        List<User> userLists = repo.findAll();
        if (userLists.isEmpty()) {
            throw new UserRuntimeException(HttpStatus.NOT_FOUND_USER_LISTS);
        }

        return mapper.toUserResponseLists(userLists);
    }

    public UserResponse createUser(UserRequest request) {
        boolean isUsernameExists = repo.existsByUsername(request.getUsername());
        boolean isEmailExists = repo.existsByEmail(request.getEmail());
        boolean isPhoneNumberExists = repo.existsByPhoneNumber(request.getPhoneNumber());

        if (isUsernameExists) {
            throw new UserRuntimeException(HttpStatus.EXISTS_USERNAME);
        }
        if (isEmailExists) {
            throw new UserRuntimeException(HttpStatus.EXISTS_EMAIL);
        }
        if (isPhoneNumberExists) {
            throw new UserRuntimeException(HttpStatus.EXISTS_PHONENUMBER);
        }

        User user = mapper.toUser(request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        repo.save(user);

        return mapper.toUserResponse(user);
    }

    public UserResponse updateUserById(String uuid, UserRequest request) {
        User user = repo.findById(uuid).orElseThrow(
                () -> new UserRuntimeException(HttpStatus.NOT_FOUND_USER)
        );

        mapper.toUserUpdate(request, user);

        if (Objects.equals(request.getUsername(), user.getUsername()))
            user.setUsername(user.getUsername());
        if (Objects.equals(request.getPassword(), user.getPassword()))
            user.setPassword(user.getPassword());
        if (Objects.equals(request.getEmail(), user.getEmail()))
            user.setEmail(user.getEmail());
        if (Objects.equals(request.getPhoneNumber(), user.getPhoneNumber()))
            user.setPhoneNumber(user.getPhoneNumber());
        if (Objects.equals(request.getFirstName(), user.getFirstName()))
            user.setFirstName(user.getFirstName());
        if (Objects.equals(request.getLastName(), user.getLastName()))
            user.setLastName(user.getLastName());
        if (Objects.equals(request.getBirthday(), user.getBirthday()))
            user.setBirthday(user.getBirthday());

        repo.save(user);

        return mapper.toUserResponse(user);
    }

    public UserResponse deleteUserById(String uuid) {
        User user = repo.findById(uuid).orElseThrow(
                () -> new UserRuntimeException(HttpStatus.NOT_FOUND_USER)
        );

        repo.delete(user);

        return mapper.toUserResponse(user);
    }

}

