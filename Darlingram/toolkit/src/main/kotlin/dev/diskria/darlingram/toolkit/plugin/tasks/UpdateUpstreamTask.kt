package dev.diskria.darlingram.toolkit.plugin.tasks

import dev.diskria.darlingram.toolkit.platforms.PlatformType

@Suppress("unused")
class UpdateUpstreamTask : GradleToolkitTask(
    "Update upstream"
) {
    override fun runTask() {
        UpdateSubmodulesTask.updateSubmodule(PlatformType.MAIN_PLATFORM, directories)
    }
}
