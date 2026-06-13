plugins {
    java
    id("org.springframework.boot") version "3.3.5"
    id("io.spring.dependency-management") version "1.1.6"
}

group = "com.savadanko"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // spring-boot-starter-data-jpa: JpaRepository + JpaSpecificationExecutor (Criteria API)
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    // H2 — встроенная in-memory БД, тесты идут без внешнего сервера
    runtimeOnly("com.h2database:h2")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.test {
    useJUnitPlatform()
}
