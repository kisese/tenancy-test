package com.basigo.project.domain.user;

import com.basigo.project.domain.user.mapper.UserMapper;
import com.basigo.project.domain.user.model.UpdateUserOrganizationRequest;
import com.basigo.project.domain.user.model.UserResponse;
import com.basigo.project.exceptions.NotFoundException;
import com.basigo.project.persistence.organization.entities.Organization;
import com.basigo.project.persistence.organization.repositories.OrganizationRepository;
import com.basigo.project.persistence.user.entities.User;
import com.basigo.project.persistence.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {

    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public Page<UserResponse> getAllUsersByFilter(Long userId,
                                                  String email,
                                                  Pageable pageable) {

        Page<User> users = userRepository.findAllByFilters(userId, email, pageable);

        return users.map(userMapper::toUserResponse);
    }

    public UserResponse updateUserOrganization(Long userId, UpdateUserOrganizationRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Organization organization = organizationRepository.findById(request.getOrganizationId())
                .orElseThrow(() -> new NotFoundException("Organization not found"));

        user.setOrganization(organization);
        return userMapper.toUserResponse(userRepository.save(user));
    }
}
