package com.basigo.project.unit.domain.auth.service;

import com.basigo.project.config.security.JwtTokenProvider;
import com.basigo.project.domain.auth.AuthService;
import com.basigo.project.domain.auth.model.LoginRequest;
import com.basigo.project.domain.auth.model.LoginResponse;
import com.basigo.project.domain.auth.model.RegistrationRequest;
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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private AuthService authService;

    private RegistrationRequest registrationRequest;
    private LoginRequest loginRequest;
    private User user;
    private UserResponse userResponse;
    private Organization organization;
    private Authentication authentication;
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        registrationRequest = new RegistrationRequest();
        registrationRequest.setUsername("testuser");
        registrationRequest.setEmail("testuser@example.com");
        registrationRequest.setPassword("password");

        loginRequest = new LoginRequest();
        loginRequest.setEmail("testuser@example.com");
        loginRequest.setPassword("password");

        user = new User();
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
        user.setPassword("encodedPassword");

        userResponse = new UserResponse();
        userResponse.setUsername("testuser");
        userResponse.setEmail("testuser@example.com");

        organization = new Organization();
        organization.setId(1L);

        authentication = mock(Authentication.class);
        userDetails = mock(UserDetails.class);
    }

    @Test
    void registerUser_success() {
        when(passwordEncoder.encode(registrationRequest.getPassword())).thenReturn("encodedPassword");
        when(userMapper.toUserResponse(any(User.class))).thenReturn(userResponse);
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponse result = authService.registerUser(registrationRequest);

        assertEquals(userResponse, result);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void authenticateUser_success() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(user));
        when(jwtTokenProvider.generateToken(userDetails)).thenReturn("token");

        LoginResponse result = authService.authenticateUser(loginRequest);

        assertEquals("token", result.getToken());
        assertEquals("testuser@example.com", result.getEmail());
    }

    @Test
    void authenticateUser_organizationNotFound() {
        user.setOrganization(organization);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(user));
        when(jwtTokenProvider.generateToken(userDetails)).thenReturn("token");
        when(organizationRepository.findById(organization.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> authService.authenticateUser(loginRequest));
    }
}
