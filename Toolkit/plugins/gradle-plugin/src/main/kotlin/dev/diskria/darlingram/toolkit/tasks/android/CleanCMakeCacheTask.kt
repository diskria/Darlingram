package dev.diskria.darlingram.toolkit.tasks.android

import dev.diskria.darlingram.toolkit.ProjectDirectories
import dev.diskria.darlingram.toolkit.utils.gradle.extensions.directories
import dev.diskria.darlingram.toolkit.utils.gradle.extensions.isTelegram

@Suppress("unused")
abstract class CleanCMakeCacheTask : AndroidToolkitTask(
    "Delete $DIRECTORY_NAME directory",
) {
    override fun runTask(directories: ProjectDirectories) {
        val cxxDirectory = directories().run {
            if (project.isTelegram()) getTelegramLibraryModule()
            else getTelegramJNIWrapperModule()
        }.resolve(DIRECTORY_NAME)
        if (cxxDirectory.exists()) {
            cxxDirectory.deleteRecursively()
        }
    }

    companion object {
        private const val DIRECTORY_NAME = ".cxx"
    }
}
