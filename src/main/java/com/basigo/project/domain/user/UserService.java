package com.basigo.project.domain.user;

import com.basigo.project.domain.user.mapper.UserMapper;
import com.basigo.project.domain.user.model.UserResponse;
import com.basigo.project.persistence.user.entities.User;
import com.basigo.project.persistence.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public Page<UserResponse> getAllUsersByFilter(Long userId,
                                                  String email,
                                                  Pageable pageable) {

        Page<User> users = userRepository.findAllByFilters(userId, email, pageable);

        return users
                .map(userMapper::toUserResponse);
    }
}
