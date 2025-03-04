package com.basigo.project.domain.auth;

import com.basigo.project.config.security.JwtTokenProvider;
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
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final List<ConditionalValidator<RegistrationRequest>> validators;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public UserResponse registerUser(RegistrationRequest request) {

        validators.stream()
                .filter(validator -> validator.shouldValidate(request))
                .forEach(validator -> validator.validate(request));

//        Organization organization = organizationRepository.findById(request.getOrganizationId())
//                .orElseThrow(() -> new NotFoundException("Organization not found"));

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
//        user.setOrganization(organization);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public LoginResponse authenticateUser(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        String token = jwtTokenProvider.generateToken(userDetails);

        if (Objects.nonNull(user.getOrganization())) {
            Organization organization = organizationRepository.findById(user.getOrganization().getId())
                    .orElseThrow(() -> new NotFoundException("Organization not found"));

            return LoginResponse.builder()
                    .token(token)
                    .email(user.getEmail())
                    .organizationId(organization.getId())
                    .build();
        }

        return LoginResponse.builder()
                .token(token)
                .email(user.getEmail())
                .build();
    }
}
