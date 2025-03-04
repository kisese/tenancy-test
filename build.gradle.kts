plugins {
    java
    id("org.springframework.boot") version "3.4.3"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.basigo.project"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

tasks.jar {
    archiveFileName.set("app.jar")
}

springBoot {
    mainClass = "com.basigo.project.ProjectBasigoApplication"
}

repositories {
    mavenCentral()
}

val mapstructVersion = "1.5.5.Final"
val tsidCreatorVersion = "5.2.0"
val commonsLangVersion = "3.12.0"
val springdocVersion = "2.8.2"
val jjwtVersion = "0.12.6"
val sqliteJdbcVersion = "3.40.0.0"
val sqliteDialectVersion = "6.6.8.Final"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.security:spring-security-core")
//    implementation("org.springframework.security:spring-security-jwt")
    implementation("io.jsonwebtoken:jjwt:${jjwtVersion}")
    implementation("org.springframework.security:spring-security-crypto")

    implementation("org.xerial:sqlite-jdbc:${sqliteJdbcVersion}")
    implementation("com.zaxxer:HikariCP")
    implementation("org.hibernate.orm:hibernate-core:${sqliteDialectVersion}")
    implementation("org.hibernate.orm:hibernate-community-dialects:${sqliteDialectVersion}")

    implementation("org.mapstruct:mapstruct:${mapstructVersion}")
    annotationProcessor("org.mapstruct:mapstruct-processor:${mapstructVersion}")

    implementation("org.apache.commons:commons-lang3:${commonsLangVersion}")
    implementation("com.github.f4b6a3:tsid-creator:${tsidCreatorVersion}")
//    implementation("org.flywaydb:flyway-core")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${springdocVersion}")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.testcontainers:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
