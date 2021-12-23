package io.gitee.nn.electron.java.task

import org.gradle.api.tasks.Exec

class ElectronBuildTask extends Exec {
    private final String outputDir = project.buildDir.getAbsolutePath() + "/electronApp"

    ElectronBuildTask() {
        this.commandLine("npx")
        this.args("electron-builder", "--config=./bin/electron-builder.json")
        this.workingDir = outputDir + "/electron-host"
    }
}
