package com.basigo.project.unit.domain.organization.service;

import com.basigo.project.domain.organization.OrgService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

//@ExtendWith(MockitoExtension.class)
//public class OrganizationServiceTest {
//
//
//}


//package com.basigo.project.domain.organization;

import com.basigo.project.domain.organization.mapper.OrganizationMapper;
import com.basigo.project.domain.organization.model.OrganizationRequest;
import com.basigo.project.domain.organization.model.OrganizationResponse;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrganizationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrganizationRepository organizationRepository;

    @Mock
    private OrganizationMapper organizationMapper;

    @InjectMocks
    private OrgService orgService;

    private OrganizationRequest request;
    private Organization organization;
    private User user;
    private OrganizationResponse organizationResponse;

    @BeforeEach
    void setUp() {
        request = new OrganizationRequest();
        request.setName("Test Organization");
        request.setLogoUrl("http://logo.url");
        request.setPrimaryColor("#000000");
        request.setSecondaryColor("#FFFFFF");
        request.setUserId(1L);

        organization = new Organization();
        organization.setName("Test Organization");
        organization.setLogoUrl("http://logo.url");
        organization.setPrimaryColor("#000000");
        organization.setSecondaryColor("#FFFFFF");

        user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        organizationResponse = new OrganizationResponse();
        organizationResponse.setName("Test Organization");
    }

    @Test
    void createOrg_success() {
        when(organizationRepository.save(any(Organization.class))).thenReturn(organization);
        when(userRepository.findById(request.getUserId())).thenReturn(Optional.of(user));
        when(organizationMapper.toOrgResponse(any(Organization.class))).thenReturn(organizationResponse);

        OrganizationResponse result = orgService.createOrg(request);

        assertEquals(organizationResponse, result);
        verify(organizationRepository).save(any(Organization.class));
        verify(userRepository).save(any(User.class));
    }

    @Test
    void createOrg_userNotFound() {
        when(userRepository.findById(request.getUserId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> orgService.createOrg(request));
    }
}