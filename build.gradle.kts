plugins {
    java
    application
    id("io.spring.dependency-management") version "1.1.6"
}

group = "com.savadanko"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    mainClass = "com.savadanko.javarepo.cron.CronDemo"
}

repositories {
    mavenCentral()
}

// BOM Spring Boot управляет версиями — сам Boot не нужен, берём только spring-context
dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:3.3.5")
    }
}

dependencies {
    // CronExpression живёт в spring-context — ни планировщик, ни Spring Boot не нужны
    implementation("org.springframework:spring-context")

    testImplementation(platform("org.junit:junit-bom:5.11.3"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}
