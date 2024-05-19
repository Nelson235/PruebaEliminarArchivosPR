plugins {
    java
    id("org.springframework.boot") version "3.2.3"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "ucr.sa"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

extra["springCloudVersion"] = "2023.0.0"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")


    implementation("org.springframework.cloud:spring-cloud-starter-feign:1.4.7.RELEASE")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("com.h2database:h2")
    implementation("org.springframework.security:spring-security-crypto:6.2.2")
    implementation("io.jsonwebtoken:jjwt:0.12.5")
    implementation("javax.xml.bind:jaxb-api:2.3.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation ("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.assertj:assertj-core:3.11.1")
    testImplementation ("com.tngtech.archunit:archunit-junit5:1.1.0")
    implementation("org.liquibase:liquibase-core")
    implementation("com.mysql:mysql-connector-j")
}


dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}