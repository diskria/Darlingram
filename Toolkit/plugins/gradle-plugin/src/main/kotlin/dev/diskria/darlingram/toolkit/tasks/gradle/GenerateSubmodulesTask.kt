package dev.diskria.darlingram.toolkit.tasks.gradle

import dev.diskria.darlingram.toolkit.submodules.PlatformType
import dev.diskria.darlingram.toolkit.utils.ProjectDirectories
import dev.diskria.darlingram.toolkit.utils.Templates
import dev.diskria.darlingram.toolkit.utils.shell.GitShell
import dev.diskria.darlingram.toolkit.utils.kotlin.utils.Constants
import java.io.File

@Suppress("unused")
abstract class GenerateSubmodulesTask : GradleToolkitTask(
    "Generate submodules"
) {
    override fun runTask(directories: ProjectDirectories) {
        val forkRoot = directories.getForkRoot()
        val config = generateGitmodulesConfig(directories, forkRoot)
        directories.getGitModules().writeText(config)
        initializeGitSubmodules(forkRoot)
    }

    private fun generateGitmodulesConfig(directories: ProjectDirectories, forkRoot: File): String =
        PlatformType.values().joinToString(Constants.Symbol.NEW_LINE) { platform ->
            val relativePath = directories.getTelegramClientSubmodule(platform)
                .relativeTo(forkRoot)
                .absolutePath
            Templates.buildSubmodule(relativePath, platform.getRepositoryUrl())
        }

    private fun initializeGitSubmodules(forkRoot: File) {
        GitShell.open(forkRoot).run {
            initSubmodule(shouldReinit = true)
            updateSubmodule(allowRecursive = true)
        }
    }
}
