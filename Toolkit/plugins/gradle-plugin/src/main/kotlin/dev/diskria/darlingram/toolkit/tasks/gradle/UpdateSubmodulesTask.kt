package dev.diskria.darlingram.toolkit.tasks.gradle

import dev.diskria.darlingram.toolkit.submodules.PlatformType
import dev.diskria.darlingram.toolkit.submodules.TelegramClients
import dev.diskria.darlingram.toolkit.utils.ProjectDirectories

@Suppress("unused")
abstract class UpdateSubmodulesTask : GradleToolkitTask(
    "Update submodules"
) {
    override fun runTask(directories: ProjectDirectories) {
        PlatformType.values().forEach { platformType ->
            TelegramClients.updateSubmodule(this, platformType)
        }
    }
}
