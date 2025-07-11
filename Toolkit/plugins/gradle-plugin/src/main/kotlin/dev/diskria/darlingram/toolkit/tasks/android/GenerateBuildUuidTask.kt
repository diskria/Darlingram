package dev.diskria.darlingram.toolkit.tasks.android

import dev.diskria.darlingram.toolkit.ProjectDirectories
import dev.diskria.darlingram.toolkit.utils.gradle.extensions.directories
import dev.diskria.darlingram.toolkit.utils.gradle.extensions.isTelegram
import dev.diskria.darlingram.tools.kotlin.extensions.generateUuid

@Suppress("unused")
abstract class GenerateBuildUuidTask : AndroidToolkitTask(
    "Generates build UUID and puts it to assets",
) {
    override fun runTask(directories: ProjectDirectories) {
        val assetsModule = when {
            project.isTelegram() -> directories().getTelegramLibraryModule()
            else -> directories().getApplicationModule()
        }
        with(assetsModule.resolve("src/main/" + ASSETS_FILE_PATH)) {
            parentFile.mkdirs()
            writeText(generateUuid())
        }
    }

    companion object {
        const val ASSETS_FILE_PATH = "assets/build_uuid.txt"
    }
}
