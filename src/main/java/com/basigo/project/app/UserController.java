package com.basigo.project.app;

import com.basigo.project.domain.auth.AuthService;
import com.basigo.project.domain.auth.model.RegistrationRequest;
import com.basigo.project.domain.auth.model.LoginRequest;
import com.basigo.project.domain.auth.model.LoginResponse;
import com.basigo.project.domain.user.UserService;
import com.basigo.project.domain.user.model.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@Tag(name = "UserController", description = "API for managing Users & Auth")
public class UserController {
    private final AuthService authService;
    private final UserService userService;

    @Operation(summary = "Create a user account successfully", description = "Creates a user account successfully")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Create a user account successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/register")
    public UserResponse register(@RequestBody @Valid RegistrationRequest registrationRequest) {
        return authService.registerUser(registrationRequest);
    }

    @Operation(summary = "Login a user successfully", description = "Login a user successfully")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login a user successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest request) {
        return authService.authenticateUser(request);
    }

    @Operation(summary = "Get all Users", description = "Retrieve a list of all registered Users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of users retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "404", description = "No users found", content = @Content)
    })
    @GetMapping("/all")
    public Page<UserResponse> getAllUsers(@RequestParam(required = false) Long id,
                                          @RequestParam(required = false) String email,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size,
                                          @RequestParam(defaultValue = "id") String sortBy,
                                          @RequestParam(defaultValue = "asc") String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return userService.getAllUsersByFilter(id, email, pageable);
    }
}
