package dev.diskria.darlingram.toolkit.submodules

import dev.diskria.darlingram.toolkit.tasks.ToolkitTask
import dev.diskria.darlingram.toolkit.utils.PropertyNames
import dev.diskria.darlingram.toolkit.utils.gradle.extensions.getProperty
import dev.diskria.darlingram.toolkit.utils.shell.GitShell
import dev.diskria.darlingram.toolkit.utils.serialization.serialize
import dev.diskria.darlingram.toolkit.utils.kotlin.extensions.fileName
import dev.diskria.darlingram.toolkit.utils.kotlin.utils.Constants
import kotlinx.serialization.ExperimentalSerializationApi
import java.io.File

object TelegramClients {

    @OptIn(ExperimentalSerializationApi::class)
    fun extractClientVersion(
        task: ToolkitTask,
        platformType: PlatformType,
        projectDirectory: File,
    ): ClientVersion {
        val directories = task.directories

        val (versionCode, versionName) = when (platformType) {
            PlatformType.ANDROID -> {
                val gradleProperties = projectDirectory.resolve(
                    fileName("gradle", Constants.File.Extension.PROPERTIES)
                )
                with(task.project) {
                    val code: Int = getProperty(gradleProperties, PropertyNames.APP_VERSION_CODE)
                    val name: String = getProperty(gradleProperties, PropertyNames.APP_VERSION_NAME)
                    code to name
                }
            }

            PlatformType.IOS -> {
                val totalCommitsCount = GitShell.open(projectDirectory).getTotalCommitsCount()
                val buildNumberOffset = directories
                    .getIOSTelegramClientSubmoduleBuildNumberOffset()
                    .readText()
                    .trim()
                    .toInt()

                val code = totalCommitsCount + buildNumberOffset
                val name = directories
                    .getIOSTelegramClientSubmoduleVersionJson()
                    .serialize<IOSVersionFile>()
                    .versionName
                code to name
            }

            PlatformType.DESKTOP -> {
                val versionFile = directories.getDesktopTelegramClientSubmoduleVersionProperties()
                val properties = versionFile.readLines().associate { line ->
                    val parts = line.trim().split(Regex("\\s+"))
                    parts.first() to parts.last()
                }
                val code = properties["AppVersion"]?.toInt()
                val name = properties["AppVersionStr"]
                code to name
            }
        }
        if (versionCode == null || versionName == null) {
            error("Can't parse version for platform $platformType from $projectDirectory")
        }
        return ClientVersion.parse(versionCode, versionName)
    }

    fun updateSubmodule(
        task: ToolkitTask,
        platformType: PlatformType,
    ) {
        val directories = task.directories
        val submodule = directories.getTelegramClientSubmodule(platformType)
        val oldVersion = extractClientVersion(task, platformType, submodule)
        GitShell.open(submodule).run {
            fetch(GitShell.ORIGIN_REMOTE_NAME)
            checkout(platformType.gitBranch)
            pull(GitShell.ORIGIN_REMOTE_NAME, platformType.gitBranch)
        }
        val newVersion = extractClientVersion(task, platformType, submodule)
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
