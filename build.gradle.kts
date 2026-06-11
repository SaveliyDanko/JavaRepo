plugins {
    java
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
    // Только API таймеров EJB. Реализацию предоставляет EE-контейнер
    // (GlassFish/Payara/WildFly), поэтому зависимость compileOnly —
    // код компилируется, но в рантайме контейнер подставит свою реализацию.
    compileOnly("jakarta.ejb:jakarta.ejb-api:4.0.1")
    // @Resource / @PostConstruct
    compileOnly("jakarta.annotation:jakarta.annotation-api:3.0.0")

    testImplementation(platform("org.junit:junit-bom:5.11.3"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    // API нужно и в тестах, чтобы скомпилировать класс таймера
    testImplementation("jakarta.ejb:jakarta.ejb-api:4.0.1")
    testImplementation("jakarta.annotation:jakarta.annotation-api:3.0.0")
    testImplementation("org.mockito:mockito-core:5.14.2")
}

tasks.test {
    useJUnitPlatform()
}
