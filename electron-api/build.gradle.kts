plugins {
    `java-library`
    `maven-publish`
}

group = rootProject.group
version = rootProject.version
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.socket:socket.io-client:2.0.1")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.4")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.15.4")
    implementation("org.springframework.boot:spring-boot-starter:3.2.3")
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
}

tasks.create("sourcesJar", Jar::class) {
    from(sourceSets.main.get().allJava)
    archiveClassifier.set("sources")
}

publishing.publications.create("mavenJava", MavenPublication::class) {
    from(components["java"])
    artifact(tasks.getByName("sourcesJar"))
}

tasks.withType<Test> {
    useJUnitPlatform()
}
