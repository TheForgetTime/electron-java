package io.gitee.nn.electron.java


import io.gitee.nn.electron.java.task.ElectronBuildTask
import io.gitee.nn.electron.java.task.ElectronRunTask
import io.gitee.nn.electron.java.utils.FileUtil
import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.CopySpec
import org.gradle.process.ExecSpec

class ElectronJavaPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        String outputDir = project.buildDir.getAbsolutePath() + "/electronApp"
        String manifestOutputDir = project.projectDir
        File manifestFile = new File(manifestOutputDir + "/electron.manifest.json")
        project.task("electronInit", group: "electronJava") {
            FileUtil.loadResourceFromJarByFolder("/electron-host", outputDir, this.getClass())
            if (!manifestFile.exists()) {
                FileUtil.loadResourceFromJarByFolder("/electron.manifest.json", manifestOutputDir, this.getClass())
            }
            FileUtil.replaceTextContent(manifestFile.getAbsolutePath(), "\\{\\{executable\\}\\}", project.getName())
            project.exec(new Action<ExecSpec>() {
                @Override
                void execute(ExecSpec execSpec) {
                    execSpec.commandLine("npm")
                    execSpec.args("i")
                    execSpec.workingDir = outputDir + "/electron-host"
                }
            })
        }

        project.tasks.create(name: "electronCopyFile", group: "electronJava") {
            doFirst {
                project.delete(outputDir + "/electron-host/bin/*")
            }
            dependsOn("electronInit")
            dependsOn("installBootDist")
            doLast {
                project.copy(new Action<CopySpec>() {
                    @Override
                    void execute(CopySpec copySpec) {
                        copySpec.from(project.buildDir.absolutePath + "/install")
                        copySpec.into(outputDir + "/electron-host/bin")
                    }
                })
                project.copy(new Action<CopySpec>() {
                    @Override
                    void execute(CopySpec copySpec) {
                        copySpec.from(manifestFile)
                        copySpec.into(outputDir + "/electron-host/bin")
                    }
                })
            }
        }

        project.tasks.create(name: "electronRun", group: "application", type: ElectronRunTask.class) {
            dependsOn("electronCopyFile")
        }

        project.tasks.create(name: "electronBuild", group: "electronJava", type: ElectronBuildTask.class) {
            dependsOn("electronCopyFile")
            doFirst {
                project.exec(new Action<ExecSpec>() {
                    @Override
                    void execute(ExecSpec execSpec) {
                        execSpec.commandLine("node")
                        execSpec.args("build-helper.js", "electron.manifest.json")
                        execSpec.workingDir = outputDir + "/electron-host"
                    }
                })
            }
        }
    }
}
