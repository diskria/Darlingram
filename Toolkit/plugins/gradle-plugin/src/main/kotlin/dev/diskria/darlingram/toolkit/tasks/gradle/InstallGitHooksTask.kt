package dev.diskria.darlingram.toolkit.tasks.gradle

import dev.diskria.darlingram.toolkit.ProjectDirectories
import dev.diskria.darlingram.tools.kotlin.utils.Shell
import java.io.File

@Suppress("unused")
abstract class InstallGitHooksTask : GradleToolkitTask(
    "Install git hooks"
) {
    override fun runTask(directories: ProjectDirectories) {
        val sourceDirectory = directories.getGitHooks()
        val gitHooksDirectoryPath = Shell.open(directories.getForkRoot()).runAndGetOutput(
            "git rev-parse --git-path hooks"
        )
        val targetDirectory = File(gitHooksDirectoryPath)
        sourceDirectory.listFiles()?.forEach { sourceFile ->
            sourceFile.copyTo(targetDirectory.resolve(sourceFile.name), overwrite = true)
        }
    }
}
