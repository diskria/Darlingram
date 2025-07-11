package dev.diskria.darlingram.toolkit.tasks.gradle

import dev.diskria.darlingram.toolkit.utils.ProjectDirectories
import dev.diskria.darlingram.toolkit.utils.shell.GitShell

@Suppress("unused")
abstract class InstallGitHooksTask : GradleToolkitTask(
    "Install git hooks"
) {
    override fun runTask(directories: ProjectDirectories) {
        val sourceDirectory = directories.getGitHooks()
        val targetDirectory = GitShell.open(directories.getForkRoot()).getHooksDirectory()
        sourceDirectory.listFiles()?.forEach { sourceFile ->
            sourceFile.copyTo(targetDirectory.resolve(sourceFile.name), overwrite = true)
        }
    }
}
