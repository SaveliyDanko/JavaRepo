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
    // Quartz Scheduler + интеграция со Spring (SchedulerFactoryBean, DI в джобах)
    implementation("org.springframework.boot:spring-boot-starter-quartz")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.awaitility:awaitility")
}

tasks.test {
    useJUnitPlatform()
}
