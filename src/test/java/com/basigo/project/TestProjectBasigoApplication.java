package com.basigo.project;

import org.springframework.boot.SpringApplication;

public class TestProjectBasigoApplication {

    public static void main(String[] args) {
        SpringApplication.from(ProjectBasigoApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
