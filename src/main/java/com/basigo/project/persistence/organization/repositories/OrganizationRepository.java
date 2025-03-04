package com.basigo.project.persistence.organization.repositories;

import com.basigo.project.persistence.organization.entities.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
}
