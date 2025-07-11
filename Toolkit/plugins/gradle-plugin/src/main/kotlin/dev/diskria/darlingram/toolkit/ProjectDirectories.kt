package dev.diskria.darlingram.toolkit

import dev.diskria.darlingram.Metadata
import dev.diskria.darlingram.toolkit.platforms.TelegramClientPlatformType
import dev.diskria.darlingram.tools.kotlin.extensions.fileName
import dev.diskria.darlingram.tools.kotlin.utils.Constants
import java.io.File

class ProjectDirectories(private val projectRoot: File) {

    private val forkRoot: File = projectRoot.parentFile

    fun getProjectRoot(): File = projectRoot

    fun getForkRoot(): File = forkRoot

    fun getUpstreamRoot(): File = getTelegramClientSubmodule(TelegramClientPlatformType.Companion.MAIN_PLATFORM)

    fun getApplicationModule(): File =
        projectRoot.resolve(Metadata.APPLICATION_MODULE_NAME)

    fun getLocalProperties(): File =
        projectRoot.resolve(
            fileName("local", Constants.File.Extension.PROPERTIES)
        )

    fun getTelegramJNIWrapperModule(): File =
        projectRoot.resolve(Metadata.TELEGRAM_JNI_WRAPPER_MODULE_NAME)

    fun getTLSchemeCodegen(): File =
        projectRoot
            .resolve(Metadata.TELEGRAM_API_MODULE_NAME)
            .resolve("build")
            .resolve("generated")
            .resolve("java")

    fun getTelegramApplicationModule(): File =
        forkRoot.resolve(Metadata.TELEGRAM_APPLICATION_MODULE_DIRECTORY)

    fun getTelegramLibraryModule(): File =
        forkRoot.resolve(Metadata.TELEGRAM_LIBRARY_MODULE_DIRECTORY)

    fun getGitHooks(): File =
        forkRoot.resolve("git-hooks")

    fun getGitModules(): File =
        forkRoot.resolve(".gitmodules")

    fun getAPK(isTelegram: Boolean): File =
        forkRoot
            .resolve(Constants.File.Extension.APK)
            .resolve(
                if (isTelegram) Metadata.TELEGRAM_NAME
                else Metadata.PROJECT_NAME
            )

    fun getTelegramApiScheme(): File =
        getTelegramClientSubmodule(TelegramClientPlatformType.DESKTOP)
            .resolve("Telegram")
            .resolve("SourceFiles")
            .resolve("mtproto")
            .resolve("scheme")
            .resolve(
                fileName("api", Constants.File.Extension.TL)
            )

    fun getTelegramAndroidManifestMinSDK23(): File =
        getTelegramLibraryModule()
            .resolve("config")
            .resolve("debug")
            .resolve(
                fileName("AndroidManifest_SDK23", Constants.File.Extension.XML)
            )

    fun getTelegramIOSVersionJson(): File =
        getTelegramClientSubmodule(TelegramClientPlatformType.IOS)
            .resolve(
                fileName("versions", Constants.File.Extension.JSON)
            )

    fun getTelegramIOSBuildNumberOffset(): File =
        getTelegramClientSubmodule(TelegramClientPlatformType.IOS)
            .resolve("build_number_offset")

    fun getTelegramDesktopVersionProperties(): File =
        getTelegramClientSubmodule(TelegramClientPlatformType.DESKTOP)
            .resolve("Telegram")
            .resolve("build")
            .resolve("version")

    fun getTelegramClientSubmodule(platformType: TelegramClientPlatformType): File =
        forkRoot
            .resolve("telegram-clients")
            .resolve(platformType.getSubmoduleDirectoryName())
}