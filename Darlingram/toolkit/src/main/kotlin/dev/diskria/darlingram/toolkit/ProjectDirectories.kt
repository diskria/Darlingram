package dev.diskria.darlingram.toolkit

import dev.diskria.darlingram.Metadata
import dev.diskria.darlingram.toolkit.platforms.PlatformType
import dev.diskria.darlingram.tools.kotlin.extensions.fileName
import dev.diskria.darlingram.tools.kotlin.utils.Constants
import java.io.File

class ProjectDirectories(private val forkRoot: File) {

    private val upstreamRoot: File = forkRoot.parentFile

    fun getForkRoot(): File = forkRoot

    fun getForkLocalProperties(): File =
        forkRoot.resolve(
            fileName("local", Constants.File.Extension.PROPERTIES)
        )

    fun getUpstreamRoot(): File = upstreamRoot

    fun getJNIWrapperModule(): File =
        forkRoot.resolve(Metadata.JNI_WRAPPER_MODULE)

    fun getApiWrapperTLSchemeCodegen(): File =
        forkRoot
            .resolve(Metadata.API_WRAPPER_MODULE)
            .resolve("build")
            .resolve("generated")
            .resolve("java")

    fun getUpstreamLibraryModule(): File =
        upstreamRoot.resolve(Metadata.UPSTREAM_LIBRARY_MODULE)

    fun getGitHooks(): File =
        upstreamRoot.resolve("git-hooks")

    fun getGitModules(): File =
        upstreamRoot.resolve(".gitmodules")

    fun getAPK(isFork: Boolean): File =
        upstreamRoot
            .resolve(Constants.File.Extension.APK)
            .resolve(
                if (isFork) Metadata.FORK_NAME
                else Metadata.UPSTREAM_NAME
            )

    fun getTelegramApiScheme(): File =
        getTelegramClientSubmodule(PlatformType.DESKTOP)
            .resolve("Telegram")
            .resolve("SourceFiles")
            .resolve("mtproto")
            .resolve("scheme")
            .resolve(
                fileName("api", Constants.File.Extension.TL)
            )

    fun getTelegramAndroidManifestMinSDK23(): File =
        getUpstreamLibraryModule()
            .resolve("config")
            .resolve("debug")
            .resolve(
                fileName("AndroidManifest_SDK23", Constants.File.Extension.XML)
            )

    fun getTelegramIOSVersionJson(): File =
        getTelegramClientSubmodule(PlatformType.IOS)
            .resolve(
                fileName("versions", Constants.File.Extension.JSON)
            )

    fun getTelegramIOSBuildNumberOffset(): File =
        getTelegramClientSubmodule(PlatformType.IOS)
            .resolve("build_number_offset")

    fun getTelegramDesktopVersionProperties(): File =
        getTelegramClientSubmodule(PlatformType.DESKTOP)
            .resolve("Telegram")
            .resolve("build")
            .resolve("version")

    fun getTelegramClientSubmodule(platformType: PlatformType): File =
        upstreamRoot
            .resolve("telegram-clients")
            .resolve(platformType.getSubmoduleDirectoryName())
}
