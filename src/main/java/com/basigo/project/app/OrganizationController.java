package com.basigo.project.app;

import com.basigo.project.domain.organization.OrgService;
import com.basigo.project.domain.organization.model.OrganizationRequest;
import com.basigo.project.domain.organization.model.OrganizationResponse;
import com.basigo.project.domain.user.model.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/org")
@Tag(name = "OrganizationController", description = "API for managing Orgs")
public class OrganizationController {

    private final OrgService orgService;

    @Operation(summary = "Create a user account successfully", description = "STK data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Create a user account successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrganizationResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/register")
    public OrganizationResponse createTenant(@RequestBody OrganizationRequest request) {
        return orgService.createOrg(request);
    }
}
