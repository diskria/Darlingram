package dev.diskria.darlingram.toolkit.utils

import dev.diskria.darlingram.Metadata
import dev.diskria.darlingram.toolkit.submodules.PlatformType
import dev.diskria.darlingram.toolkit.utils.kotlin.extensions.asDirectoryOrThrow
import dev.diskria.darlingram.toolkit.utils.kotlin.extensions.asFileOrThrow
import dev.diskria.darlingram.toolkit.utils.kotlin.extensions.fileName
import dev.diskria.darlingram.toolkit.utils.kotlin.utils.Constants
import java.io.File

class ProjectDirectories(private val projectRoot: File) {

    private val forkRoot: File =
        projectRoot.parentFile.asDirectoryOrThrow()

    fun getProjectRoot(): File =
        projectRoot

    fun getForkRoot(): File =
        forkRoot

    fun getUpstreamRoot(): File =
        getTelegramClientSubmodule(PlatformType.MAIN_PLATFORM)
            .asDirectoryOrThrow()

    fun getApplicationModule(): File =
        projectRoot
            .resolve(Metadata.APPLICATION_MODULE_NAME)
            .asDirectoryOrThrow()

    fun getLocalProperties(): File =
        projectRoot
            .resolve(fileName("local", Constants.File.Extension.PROPERTIES))
            .asFileOrThrow()

    fun getTelegramJNIWrapperModule(): File =
        projectRoot
            .resolve(Metadata.TELEGRAM_JNI_WRAPPER_MODULE_NAME)
            .asDirectoryOrThrow()

    fun getTLSchemeCodegen(): File =
        projectRoot
            .resolve(Metadata.TELEGRAM_API_MODULE_NAME)
            .resolve("build")
            .resolve("generated")
            .resolve("java")
            .asFileOrThrow()

    fun getTelegramApplicationModule(): File =
        forkRoot
            .resolve(Metadata.TELEGRAM_APPLICATION_MODULE_DIRECTORY)
            .asDirectoryOrThrow()

    fun getTelegramLibraryModule(): File =
        forkRoot
            .resolve(Metadata.TELEGRAM_LIBRARY_MODULE_DIRECTORY)
            .asDirectoryOrThrow()

    fun getGitHooks(): File =
        forkRoot
            .resolve("git-hooks")
            .asDirectoryOrThrow()

    fun getGitModules(): File =
        forkRoot
            .resolve(".gitmodules")
            .asFileOrThrow()

    fun getExtractedAPKs(isTelegram: Boolean): File =
        forkRoot
            .resolve(Constants.File.Extension.APK)
            .resolve(
                if (isTelegram) Metadata.TELEGRAM_NAME
                else Metadata.PROJECT_NAME
            )
            .asDirectoryOrThrow()

    fun getTelegramApiScheme(): File =
        getTelegramClientSubmodule(PlatformType.DESKTOP)
            .resolve("Telegram")
            .resolve("SourceFiles")
            .resolve("mtproto")
            .resolve("scheme")
            .resolve(fileName("api", Constants.File.Extension.TL))
            .asFileOrThrow()

    fun getTelegramAndroidManifestSDK23(): File =
        getTelegramLibraryModule()
            .resolve("config")
            .resolve("debug")
            .resolve(fileName("AndroidManifest_SDK23", Constants.File.Extension.XML))
            .asFileOrThrow()

    fun getIOSTelegramClientSubmoduleVersionJson(): File =
        getTelegramClientSubmodule(PlatformType.IOS)
            .resolve(fileName("versions", Constants.File.Extension.JSON))
            .asFileOrThrow()

    fun getIOSTelegramClientSubmoduleBuildNumberOffset(): File =
        getTelegramClientSubmodule(PlatformType.IOS)
            .resolve("build_number_offset")
            .asFileOrThrow()

    fun getDesktopTelegramClientSubmoduleVersionProperties(): File =
        getTelegramClientSubmodule(PlatformType.DESKTOP)
            .resolve("Telegram")
            .resolve("build")
            .resolve("version")
            .asFileOrThrow()

    fun getTelegramClientSubmodule(platformType: PlatformType): File =
        forkRoot
            .resolve("telegram-clients")
            .resolve(platformType.getSubmoduleDirectoryName())
            .asDirectoryOrThrow()
}
