package com.basigo.project.domain.auth.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegistrationRequest {

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid email address")
    private String email;

//    @Pattern(
//            regexp = "^(?=.*\\d)(?=.*[a-zA-Z])(?=.*[@#$%^&+=]).{8,}$",
//            message = "Password must be at least 8 characters long and include a digit, a letter, and a special character"
//    )
    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Repeat password is required")
    private String repeatPassword;

    @Positive(message = "Organization ID must be a positive number")
    @Schema(description = "The ID of the Organization the user is registering for", example = "12345")
    private Long organizationId;

    @AssertTrue(message = "Passwords do not match")
    public boolean isPasswordMatch() {
        return password.equals(repeatPassword);
    }
}
