package dev.diskria.darlingram.toolkit.tasks.gradle

import dev.diskria.darlingram.toolkit.ProjectDirectories
import dev.diskria.darlingram.toolkit.platforms.TelegramClientPlatformType
import dev.diskria.darlingram.toolkit.utils.gradle.shell.GitShell
import dev.diskria.darlingram.tools.kotlin.utils.Constants

@Suppress("unused")
abstract class GenerateSubmodulesTask : GradleToolkitTask(
    "Generate submodules"
) {
    override fun runTask(directories: ProjectDirectories) {
        val forkRoot = directories.getForkRoot()
        val submoduleConfigurations = TelegramClientPlatformType.values().map { platformType ->
            val path = directories.getTelegramClientSubmodule(platformType).relativeTo(forkRoot)
            val gitUrl = platformType.getRepositoryUrl()
            """
            [submodule "$path"]
                path = $path
                url = $gitUrl
            """.trimIndent()
        }
        directories.getGitModules().writeText(
            submoduleConfigurations.joinToString(Constants.Symbol.NEW_LINE)
        )
        GitShell.open(forkRoot).run {
            initSubmodule(shouldReinit = true)
            updateSubmodule(allowRecursive = true)
        }
    }
}
