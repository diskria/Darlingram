package dev.diskria.darlingram.toolkit.platforms

import dev.diskria.darlingram.toolkit.ProjectDirectories
import dev.diskria.darlingram.tools.kotlin.extensions.fileName
import dev.diskria.darlingram.tools.kotlin.extensions.readByLines
import dev.diskria.darlingram.tools.kotlin.utils.Constants
import dev.diskria.darlingram.tools.kotlin.utils.Semver
import dev.diskria.darlingram.tools.kotlin.utils.Shell
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.File
import java.util.Properties

object TelegramClientVersionExtractor {

    private val iOSVersionJson: Json = Json { ignoreUnknownKeys = true }

    @OptIn(ExperimentalSerializationApi::class)
    fun extract(
        directories: ProjectDirectories,
        platformType: TelegramClientPlatformType,
        projectDirectory: File,
    ): TelegramClientVersion =
        when (platformType) {
            TelegramClientPlatformType.ANDROID -> {
                val propertiesFile = projectDirectory.resolve(
                    fileName("gradle", Constants.File.Extension.PROPERTIES)
                )
                val properties = Properties().apply {
                    load(propertiesFile.inputStream())
                }
                val versionCode = properties.getProperty("APP_VERSION_CODE", null)?.toInt()
                val versionName = properties.getProperty("APP_VERSION_NAME", null)
                if (versionCode == null || versionName == null) {
                    error("Parsing error from $propertiesFile")
                }
                TelegramClientVersion(versionCode, Semver.parse(versionName))
            }

            TelegramClientPlatformType.IOS -> {
                val commitsCount = Shell.open(projectDirectory).runAndGetOutput(
                    "git rev-list --count HEAD",
                ).toInt()
                val buildNumberOffset = directories
                    .getTelegramIOSBuildNumberOffset()
                    .readText()
                    .trim()
                    .toInt()
                val versionCode = commitsCount + buildNumberOffset
                val versionName = iOSVersionJson.decodeFromStream<IOSVersionFile>(
                    directories.getTelegramIOSVersionJson().inputStream()
                ).versionName
                TelegramClientVersion(versionCode, Semver.parse(versionName))
            }

            TelegramClientPlatformType.DESKTOP -> {
                val versionFile = directories.getTelegramDesktopVersionProperties()
                val properties = mutableMapOf<String, String>()
                versionFile.readByLines { line ->
                    val parts = line.trim().split(Regex("\\s+"))
                    properties.put(parts.first(), parts.last())
                }
                val versionCode = properties["AppVersion"]?.toInt()
                val versionName = properties["AppVersionStr"]
                if (versionCode == null || versionName == null) {
                    error("Parsing error from $versionFile")
                }
                TelegramClientVersion(versionCode, Semver.parse(versionName))
            }
        }
}
