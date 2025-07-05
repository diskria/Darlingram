package dev.diskria.darlingram.toolkit.plugin.tasks

import dev.diskria.darlingram.toolkit.extensions.isFork

@Suppress("unused")
abstract class CleanCMakeCacheTask : GradleToolkitTask(
    "Clean CMake cache"
) {
    override fun runTask() {
        val cxxDirectory = directories.run {
            if (project.isFork()) getJNIWrapperModule()
            else getUpstreamLibraryModule()
        }.resolve(DIRECTORY_NAME)
        if (cxxDirectory.exists()) {
            cxxDirectory.deleteRecursively()
        }
    }

    companion object {
        private const val DIRECTORY_NAME = ".cxx"
    }
}
