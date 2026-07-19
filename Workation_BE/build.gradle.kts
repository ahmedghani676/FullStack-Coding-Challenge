plugins {
    java
    id("org.springframework.boot") version "4.1.0"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.workflex"
version = "0.0.1-SNAPSHOT"
description = "Workation platform backend"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(26)
    }
}

// Lombok is compile-time only; make it available to the compileOnly classpath too.
configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webmvc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.opencsv:opencsv:5.9")
    runtimeOnly("com.h2database:h2")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
    testImplementation("org.springframework.boot:spring-boot-starter-restclient-test")
    testCompileOnly("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

// Only produce the executable boot jar (no extra plain jar) — keeps Docker COPY simple.
tasks.named<Jar>("jar") {
    enabled = false
}
