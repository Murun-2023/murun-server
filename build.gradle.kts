import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id ("org.springframework.boot") version "2.6.5"
    id ("io.spring.dependency-management") version "1.1.0"
    id ("org.jetbrains.kotlin.jvm") version "1.5.21"
    id ("org.jetbrains.kotlin.plugin.spring") version "1.5.21"
    id("org.jetbrains.kotlin.plugin.jpa") version "1.5.21"
    kotlin("kapt") version "1.7.10"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_16

repositories {
    mavenCentral()
}

dependencies {
    implementation ("org.springframework.boot:spring-boot-starter-web")
    implementation ("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation ("org.jetbrains.kotlin:kotlin-reflect")
    implementation ("com.amazonaws:aws-java-sdk-s3:1.12.429")
    implementation ("org.springframework.cloud:spring-cloud-starter-aws:2.2.6.RELEASE")
    implementation ("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation ("com.querydsl:querydsl-jpa")
    kapt("com.querydsl:querydsl-apt:5.0.0:jpa")
    implementation ("org.postgresql:postgresql:42.3.3")
    implementation ("org:jaudiotagger:2.0.3")
    //swagger
    implementation("io.springfox:springfox-swagger-ui:3.0.0")
    implementation("io.springfox:springfox-boot-starter:3.0.0")

    implementation ("com.h2database:h2") // h2 : implementation


    testImplementation ("org.springframework.boot:spring-boot-starter-test")
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.Embeddable")
    annotation("javax.persistence.MappedSuperclass")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "16"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
