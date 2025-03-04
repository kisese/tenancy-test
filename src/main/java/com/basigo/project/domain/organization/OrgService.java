package com.basigo.project.domain.organization;

import com.basigo.project.domain.organization.mapper.OrganizationMapper;
import com.basigo.project.domain.organization.model.OrganizationRequest;
import com.basigo.project.domain.organization.model.OrganizationResponse;
import com.basigo.project.exceptions.NotFoundException;
import com.basigo.project.persistence.organization.entities.Organization;
import com.basigo.project.persistence.organization.repositories.OrganizationRepository;
import com.basigo.project.persistence.user.entities.User;
import com.basigo.project.persistence.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrgService {

    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;
    private final OrganizationMapper organizationMapper;

    public OrganizationResponse createOrg(OrganizationRequest request) {
        Organization organization = new Organization();
        organization.setName(request.getName());
        organization.setLogoUrl(request.getLogoUrl());
        organization.setPrimaryColor(request.getPrimaryColor());
        organization.setSecondaryColor(request.getSecondaryColor());
        organizationRepository.save(organization);


        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));
        user.setOrganization(organization);
        userRepository.save(user);

        return organizationMapper.toOrgResponse(organization);
    }
}
