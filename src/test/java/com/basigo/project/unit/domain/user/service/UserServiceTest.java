package com.basigo.project.unit.domain.user.service;

import com.basigo.project.domain.user.UserService;
import com.basigo.project.domain.user.model.UserResponse;
import com.basigo.project.persistence.user.repositories.UserRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.basigo.project.domain.user.mapper.UserMapper;
import com.basigo.project.persistence.user.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserResponse userResponse;
    private Page<User> userPage;
    private Page<UserResponse> userResponsePage;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");

        userResponse = new UserResponse();
        userResponse.setUsername("testuser");
        userResponse.setEmail("testuser@example.com");

        userPage = new PageImpl<>(Collections.singletonList(user));
        userResponsePage = new PageImpl<>(Collections.singletonList(userResponse));
    }

    @Test
    void getAllUsersByFilter_success() {
        Pageable pageable = PageRequest.of(0, 10);
        when(userRepository.findAllByFilters(any(Long.class), any(String.class), any(Pageable.class))).thenReturn(userPage);
        when(userMapper.toUserResponse(any(User.class))).thenReturn(userResponse);

        Page<UserResponse> result = userService.getAllUsersByFilter(1L, "testuser@example.com", pageable);

        assertEquals(userResponsePage, result);
    }
}