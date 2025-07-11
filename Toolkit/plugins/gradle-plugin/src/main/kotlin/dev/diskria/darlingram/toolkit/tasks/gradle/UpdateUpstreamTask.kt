package dev.diskria.darlingram.toolkit.tasks.gradle

import dev.diskria.darlingram.toolkit.submodules.PlatformType
import dev.diskria.darlingram.toolkit.submodules.TelegramClients
import dev.diskria.darlingram.toolkit.utils.ProjectDirectories

@Suppress("unused")
class UpdateUpstreamTask : GradleToolkitTask(
    "Update upstream"
) {
    override fun runTask(directories: ProjectDirectories) {
        TelegramClients.updateSubmodule(this, PlatformType.MAIN_PLATFORM)
    }
}
