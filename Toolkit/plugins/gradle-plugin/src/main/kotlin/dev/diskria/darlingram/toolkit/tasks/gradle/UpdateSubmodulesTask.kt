package dev.diskria.darlingram.toolkit.tasks.gradle

import dev.diskria.darlingram.toolkit.ProjectDirectories
import dev.diskria.darlingram.toolkit.platforms.TelegramClientVersionExtractor
import dev.diskria.darlingram.toolkit.platforms.TelegramClientPlatformType
import dev.diskria.darlingram.tools.kotlin.utils.Shell

@Suppress("unused")
abstract class UpdateSubmodulesTask : GradleToolkitTask(
    "Update submodules"
) {
    override fun runTask(directories: ProjectDirectories) {
        TelegramClientPlatformType.values().forEach { platformType ->
            updateSubmodule(platformType, directories)
        }
    }

    companion object {
        fun updateSubmodule(platformType: TelegramClientPlatformType, directories: ProjectDirectories) {
            val submodule = directories.getTelegramClientSubmodule(platformType)
            val oldVersion = TelegramClientVersionExtractor.extract(directories, platformType, submodule)
            val gitBranch = platformType.gitBranch
            Shell.open(submodule).run {
                run("git fetch origin")
                run("git checkout $gitBranch")
                run("git pull origin $gitBranch")
            }
            val newVersion = TelegramClientVersionExtractor.extract(directories, platformType, submodule)
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
