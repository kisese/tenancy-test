package com.basigo.project.domain.organization.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrganizationResponse {
    private Long id;
    private String name;
    private String logoUrl;
    private String primaryColor;
    private String secondaryColor;
}
