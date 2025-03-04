package com.basigo.project.persistence.organization.entities;

import com.basigo.project.persistence.user.entities.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.*;

@Data
@Entity
@Table(name = "tenants")
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "logo_url", nullable = false)
    private String logoUrl;

    @Column(name = "primary_color", nullable = false)
    private String primaryColor;

    @Column(name = "secondary_color", nullable = false)
    private String secondaryColor;

    @OneToMany(mappedBy = "organization")
    private List<User> users = new ArrayList<>();
}
