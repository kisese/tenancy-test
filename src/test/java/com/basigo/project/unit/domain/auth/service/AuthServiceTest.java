package com.basigo.project.unit.domain.auth.service;

import com.basigo.project.domain.auth.AuthService;
import com.basigo.project.domain.auth.model.RegistrationRequest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.basigo.project.domain.user.mapper.UserMapper;
import com.basigo.project.domain.user.model.UserResponse;
import com.basigo.project.domain.validator.ConditionalValidator;
import com.basigo.project.exceptions.NotFoundException;
import com.basigo.project.persistence.organization.entities.Organization;
import com.basigo.project.persistence.organization.repositories.OrganizationRepository;
import com.basigo.project.persistence.user.entities.User;
import com.basigo.project.persistence.user.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrganizationRepository organizationRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    @Mock
    private List<ConditionalValidator<RegistrationRequest>> validators;

    @InjectMocks
    private AuthService authService;

    private RegistrationRequest request;
    private Organization organization;
    private User user;
    private UserResponse userResponse;

    @BeforeEach
    void setUp() {
        request = new RegistrationRequest();
        request.setUsername("testuser");
        request.setEmail("testuser@example.com");
        request.setPassword("password");
        request.setOrganizationId(1L);

        organization = new Organization();
        organization.setId(1L);

        user = new User();
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
        user.setPassword("encodedPassword");
        user.setOrganization(organization);

        userResponse = new UserResponse();
        userResponse.setUsername("testuser");
        userResponse.setEmail("testuser@example.com");
    }

    @Test
    void registerUser_success() {
        when(organizationRepository.findById(request.getOrganizationId())).thenReturn(Optional.of(organization));
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        when(userMapper.toUserResponse(any(User.class))).thenReturn(userResponse);

        UserResponse result = authService.registerUser(request);

        assertEquals(userResponse, result);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void registerUser_organizationNotFound() {
        when(organizationRepository.findById(request.getOrganizationId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> authService.registerUser(request));
    }
}