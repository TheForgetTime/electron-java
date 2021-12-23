plugins {
    `java-library`
    `maven-publish`
}

group = rootProject.group
version = rootProject.version
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.socket:socket.io-client:2.0.1")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.0")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.13.0")
    implementation("org.springframework.boot:spring-boot-starter:2.6.0")
    compileOnly("org.projectlombok:lombok:1.18.22")
    annotationProcessor("org.projectlombok:lombok:1.18.22")
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