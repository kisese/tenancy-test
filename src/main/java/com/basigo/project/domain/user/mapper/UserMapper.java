package com.basigo.project.domain.user.mapper;

import com.basigo.project.domain.user.model.UserResponse;
import com.basigo.project.persistence.user.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toUserResponse(User user);
}