package dev.diskria.darlingram.toolkit.plugin.tasks

import dev.diskria.darlingram.tools.kotlin.utils.Shell
import java.io.File

@Suppress("unused")
abstract class InstallGitHooksTask : GradleToolkitTask(
    "Install git hooks"
) {
    override fun runTask() {
        val sourceDirectory = directories.getGitHooks()
        val gitHooksDirectoryPath = Shell(directories.getForkRoot()).runWithOutput(
            "git rev-parse --git-path hooks"
        )
        val targetDirectory = File(gitHooksDirectoryPath)
        sourceDirectory.listFiles()?.forEach { sourceFile ->
            sourceFile.copyTo(targetDirectory.resolve(sourceFile.name), overwrite = true)
        }
    }
}
