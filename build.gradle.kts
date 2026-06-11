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
    // Spring JMS + ActiveMQ Artemis (брокер запускается встроенно — embedded)
    implementation("org.springframework.boot:spring-boot-starter-artemis")
    implementation("org.apache.activemq:artemis-jakarta-server")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.awaitility:awaitility")
}

tasks.test {
    useJUnitPlatform()
}
