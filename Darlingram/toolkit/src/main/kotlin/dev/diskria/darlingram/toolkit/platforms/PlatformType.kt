package dev.diskria.darlingram.toolkit.platforms

import dev.diskria.darlingram.tools.kotlin.extensions.invertCase
import java.util.Locale

enum class PlatformType(
    val gitBranch: String,
    val repositoryUrl: String,
) {

    ANDROID(
        "master",
        "https://github.com/DrKLO/Telegram"
    ),
    IOS(
        "master",
        "https://github.com/TelegramMessenger/Telegram-iOS"
    ),
    DESKTOP(
        "dev",
        "https://github.com/telegramdesktop/tdesktop"
    );

    fun getGitUrl(): String =
        "$repositoryUrl.git"

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
        val MAIN_PLATFORM: PlatformType = ANDROID
    }
}
