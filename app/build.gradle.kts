buildscript {
    repositories {
        mavenLocal()
    }
    dependencies {
        classpath("io.gitee.n__n:electron-java-plugin:0.0.1")
    }
}
plugins {
    application
    java
    id("org.springframework.boot") version "2.6.0"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
}

apply(plugin = "io.gitee.n__n.electron.java")

group = rootProject.group
version = rootProject.version
java.sourceCompatibility = JavaVersion.VERSION_11
application {
    mainClass.set("io.gitee.nn.Program")
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":electron-api"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    compileOnly("org.jetbrains:annotations:23.0.0")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.projectlombok:lombok")
}

tasks.withType<Test> {
    useJUnitPlatform()
}