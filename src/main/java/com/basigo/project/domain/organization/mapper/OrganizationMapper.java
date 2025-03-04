package com.basigo.project.domain.organization.mapper;

import com.basigo.project.domain.organization.model.OrganizationResponse;
import com.basigo.project.persistence.organization.entities.Organization;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrganizationMapper {

    OrganizationResponse toOrgResponse(Organization organization);
}