package io.gitee.nn.electron.java.task

import org.gradle.api.tasks.Exec

class ElectronRunTask extends Exec {
    private final String outputDir = project.buildDir.getAbsolutePath() + "/electronApp"

    ElectronRunTask() {
        this.commandLine("npm")
        this.args("run", "start")
        this.workingDir = outputDir + "/electron-host"
    }
}
