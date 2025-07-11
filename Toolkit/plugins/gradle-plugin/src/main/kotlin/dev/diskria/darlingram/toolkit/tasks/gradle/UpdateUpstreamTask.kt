package dev.diskria.darlingram.toolkit.tasks.gradle

import dev.diskria.darlingram.toolkit.ProjectDirectories
import dev.diskria.darlingram.toolkit.platforms.TelegramClientPlatformType

@Suppress("unused")
class UpdateUpstreamTask : GradleToolkitTask(
    "Update upstream"
) {
    override fun runTask(directories: ProjectDirectories) {
        UpdateSubmodulesTask.updateSubmodule(TelegramClientPlatformType.MAIN_PLATFORM, directories)
    }
}
