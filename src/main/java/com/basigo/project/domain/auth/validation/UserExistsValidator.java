package com.basigo.project.domain.auth.validation;

import com.basigo.project.domain.auth.model.RegistrationRequest;
import com.basigo.project.domain.validator.ConditionalValidator;
import com.basigo.project.exceptions.BadRequestException;
import com.basigo.project.persistence.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserExistsValidator implements ConditionalValidator<RegistrationRequest> {
    private final UserRepository userRepository;

    @Override
    public boolean shouldValidate(RegistrationRequest request) {
        return true;
    }

    @Override
    public void validate(RegistrationRequest request) {
        userRepository.findByEmail(request.getEmail())
                .ifPresent(user -> {
                    throw new BadRequestException("Email already exists");
                });

        userRepository.findByEmail(request.getUsername())
                .ifPresent(user -> {
                    throw new BadRequestException("Username already exists");
                });

    }
}