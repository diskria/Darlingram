package dev.diskria.darlingram.toolkit.platforms

import dev.diskria.darlingram.tools.kotlin.extensions.invertCase
import dev.diskria.darlingram.tools.kotlin.utils.Constants
import okhttp3.HttpUrl
import java.util.Locale

enum class TelegramClientPlatformType(
    val gitBranch: String,
    val ownerName: String,
    val projectName: String,
) {
    ANDROID(
        gitBranch = "master",
        ownerName = "DrKLO",
        projectName = "Telegram"
    ),
    IOS(
        gitBranch = "master",
        ownerName = "TelegramMessenger",
        projectName = "Telegram-iOS"
    ),
    DESKTOP(
        gitBranch = "dev",
        ownerName = "telegramdesktop",
        projectName = "tdesktop"
    );

    fun getRepositoryUrl(): String =
        HttpUrl.Builder()
            .scheme(Constants.Web.HTTPS_SCHEME)
            .host("github.com")
            .addPathSegment(ownerName)
            .addPathSegment(projectName + "." + Constants.VersionControlSystem.GIT)
            .build()
            .toString()

    fun getSubmoduleDirectoryName(): String =
        name.lowercase(Locale.ROOT)

    fun getDisplayName(): String =
        name
            .lowercase(Locale.ROOT)
            .replaceFirstChar { it.uppercase() }
            .let {
                if (this == IOS) it.invertCase()
                else it
            }

    companion object {
        val MAIN_PLATFORM: TelegramClientPlatformType = ANDROID
    }
}
