package dev.diskria.darlingram.toolkit.plugin.tasks

import dev.diskria.darlingram.toolkit.ProjectDirectories
import dev.diskria.darlingram.toolkit.platforms.ClientVersionExtractor
import dev.diskria.darlingram.toolkit.platforms.PlatformType
import dev.diskria.darlingram.tools.kotlin.utils.Shell

@Suppress("unused")
abstract class UpdateSubmodulesTask : GradleToolkitTask(
    "Update submodules"
) {
    override fun runTask() {
        PlatformType.values().forEach { platformType ->
            updateSubmodule(platformType, directories)
        }
    }

    companion object {
        fun updateSubmodule(platformType: PlatformType, directories: ProjectDirectories) {
            val submodule = directories.getTelegramClientSubmodule(platformType)
            val oldVersion = ClientVersionExtractor.extract(directories, platformType, submodule)
            val gitBranch = platformType.gitBranch
            Shell(submodule).run {
                run("git fetch origin")
                run("git checkout $gitBranch")
                run("git pull origin $gitBranch")
            }
            val newVersion = ClientVersionExtractor.extract(directories, platformType, submodule)
            val platformName = platformType.getDisplayName()
            if (newVersion != oldVersion) {
                println(
                    "✅ $platformName updated: " +
                            "${oldVersion.getDisplayName()} → ${newVersion.getDisplayName()}"
                )
            } else {
                println(
                    "ℹ\uFE0F $platformName already up-to-date, " +
                            "current version: ${oldVersion.getDisplayName()}"
                )
            }
        }
    }
}
