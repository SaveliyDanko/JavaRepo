plugins {
    java
    application
}

group = "com.savadanko"
version = "0.0.1-SNAPSHOT"

application {
    mainClass = "com.savadanko.javarepo.scheduler.SchedulerDemo"
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.11.3"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}
