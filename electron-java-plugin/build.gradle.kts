plugins {
    `java-gradle-plugin`
    `groovy-gradle-plugin`
    id("com.gradle.plugin-publish") version "0.18.0"
    `maven-publish`
}
repositories {
    mavenCentral()
}

group = rootProject.group
version = rootProject.version

gradlePlugin {
    plugins {
        create("electron-java") {
            id = "io.gitee.n__n.electron.java"
            implementationClass = "io.gitee.nn.electron.java.ElectronJavaPlugin"
        }
    }
}

tasks {
    getByName("processResources", ProcessResources::class) {
        doFirst {
            exec {
                commandLine("npm")
                args("i")
                workingDir(projectDir.absolutePath + "/src/main/resources/electron-host")
            }
            exec {
                commandLine("npm")
                args("run", "tsc")
                workingDir(projectDir.absolutePath + "/src/main/resources/electron-host")
            }
        }
        exclude("electron-host/node_modules")
        exclude("electron-host/package-lock.json")
        exclude("electron-host/**/*.ts")
    }
    pluginBundle {
        website = "https://gitee.com/n__n/electron-java"
        vcsUrl = "https://gitee.com/n__n/electron-java.git"
        description = "A bridge for electron and java"

        (plugins){
            "electron-java"{
                displayName = "Electron Java plugin"
                tags = listOf("Electron", "Java", "Spring boot")
                version = version
            }
        }
    }
}