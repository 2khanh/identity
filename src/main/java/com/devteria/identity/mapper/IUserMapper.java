package com.devteria.identity.mapper;

import com.devteria.identity.dto.request.UserRequest;
import com.devteria.identity.dto.response.UserResponse;
import com.devteria.identity.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IUserMapper {

    User toUser(UserRequest request);

    UserResponse toUserResponse(User user);

    List<UserResponse> toUserResponseLists(List<User> userLists);

    void toUserUpdate(UserRequest request, @MappingTarget User user);

}
