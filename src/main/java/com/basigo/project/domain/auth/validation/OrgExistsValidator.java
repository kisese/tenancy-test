//package com.basigo.project.domain.auth.validation;
//
//import com.basigo.project.domain.auth.model.RegistrationRequest;
//import com.basigo.project.domain.validator.ConditionalValidator;
//import com.basigo.project.exceptions.NotFoundException;
//import com.basigo.project.persistence.organization.repositories.OrganizationRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class OrgExistsValidator implements ConditionalValidator<RegistrationRequest> {
//    private final OrganizationRepository organizationRepository;
//
//    @Override
//    public boolean shouldValidate(RegistrationRequest request) {
//        return true;
//    }
//
//    @Override
//    public void validate(RegistrationRequest request) {
//        organizationRepository.findById(request.getOrganizationId())
//                .orElseThrow(() -> new NotFoundException("Organization not found"));
//
//    }
//}