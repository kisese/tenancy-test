package com.basigo.project.persistence.user.entities;

import com.basigo.project.domain.auth.enums.Role;
import com.basigo.project.persistence.organization.entities.Organization;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "email", nullable = false)
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role = Role.ROLE_ADMIN;

    @ManyToOne
    @JoinColumn(name = "organization_id", nullable = true)
    private Organization organization;
}
