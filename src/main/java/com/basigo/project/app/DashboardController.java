package com.basigo.project.app;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public String getDashboard() {
        return "Welcome to your dashboard!";
    }
}
