import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.6.6"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.spring") version "1.6.10"
    kotlin("plugin.jpa") version "1.6.10"
    kotlin("plugin.allopen") version "1.4.31"
}

group = "it.group24"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.assertj:assertj-core:3.22.0")
    implementation("javax.validation:validation-api:2.0.1.Final")
    implementation("org.hibernate:hibernate-validator:7.0.4.Final")
    implementation("org.json:json:20090211")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation(kotlin("test"))

    // Postgres connection library
    implementation("org.postgresql", "postgresql", "42.2.5")
    runtimeOnly("mysql:mysql-connector-java")
    testImplementation ("org.testcontainers:junit-jupiter:1.17.1")
    testImplementation("org.testcontainers:postgresql:1.17.1")
    dependencyManagement {
        imports {
            mavenBom("org.testcontainers:testcontainers-bom:1.16.3")
        }
    }

    // Rate Limiter
    implementation("com.github.vladimir-bukhtoyarov:bucket4j-core:7.4.0")

    // Email library
    implementation("org.springframework.boot:spring-boot-starter-mail")

    // Security
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.security:spring-security-test")

    // JWT
    implementation("io.jsonwebtoken:jjwt-api:0.11.2")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.2")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.2")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
