package dev.diskria.darlingram.toolkit.plugin.tasks

import dev.diskria.darlingram.toolkit.platforms.PlatformType
import dev.diskria.darlingram.tools.kotlin.utils.Constants
import dev.diskria.darlingram.tools.kotlin.utils.Shell

@Suppress("unused")
abstract class GenerateSubmodulesTask : GradleToolkitTask(
    "Generate submodules"
) {
    override fun runTask() {
        val forkRoot = directories.getForkRoot()
        val submoduleConfigurations = PlatformType.values().map { platformType ->
            val path = directories.getTelegramClientSubmodule(platformType).relativeTo(forkRoot)
            val gitUrl = platformType.getGitUrl()
            """
            [submodule "$path"]
                path = $path
                url = $gitUrl
            """.trimIndent()
        }
        directories.getGitModules().writeText(
            submoduleConfigurations.joinToString(Constants.Symbol.NEW_LINE)
        )
        Shell(forkRoot).run {
            run("git submodule deinit --all --force")
            run("git submodule init")
            run("git submodule update --init --recursive")
        }
    }
}
