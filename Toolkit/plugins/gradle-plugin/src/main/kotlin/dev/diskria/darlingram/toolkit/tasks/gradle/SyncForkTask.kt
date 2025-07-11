package dev.diskria.darlingram.toolkit.tasks.gradle

import dev.diskria.darlingram.Metadata
import dev.diskria.darlingram.toolkit.ProjectDirectories
import dev.diskria.darlingram.toolkit.platforms.TelegramClientVersionExtractor
import dev.diskria.darlingram.toolkit.platforms.TelegramClientPlatformType
import dev.diskria.darlingram.tools.kotlin.utils.Shell
import java.util.Locale

@Suppress("unused")
abstract class SyncForkTask : GradleToolkitTask(
    "Merge upstream changes into fork"
) {
    override fun runTask(directories: ProjectDirectories) {
        val forkRoot = directories.getForkRoot()
        val remoteName = Metadata.TELEGRAM_NAME.lowercase(Locale.ROOT)
        val remoteUrl = TelegramClientPlatformType.MAIN_PLATFORM.getRepositoryUrl()
        val gitBranch = TelegramClientPlatformType.MAIN_PLATFORM.gitBranch
        val oldVersion = TelegramClientVersionExtractor.extract(
            directories,
            TelegramClientPlatformType.MAIN_PLATFORM,
            forkRoot
        )
        val shell = Shell.open(forkRoot)
        if (shell.runAndGetOutput("git remote get-url $remoteName") != remoteUrl) {
            shell.run("git remote add $remoteName $remoteUrl")
        }
        shell.run("git fetch $remoteName")
        val aheadCount = shell.runAndGetOutput(
            "git rev-list HEAD..$remoteName/$gitBranch --count"
        ).toIntOrNull() ?: error("Failed to parse ahead count.")
        if (aheadCount > 0) {
            val hasWorkingChanges = shell.run("git diff --quiet").not()
            val hasStagedChanges = shell.run("git diff --cached --quiet").not()
            if (hasWorkingChanges || hasStagedChanges) {
                error(
                    """
                    🛑 Local changes detected!
                    💡 You must commit or stash your changes before updating from upstream.
                    """.trimIndent()
                )
            }
            val isMergedSuccessfully = shell.run("git merge $remoteName $gitBranch")
            if (isMergedSuccessfully) {
                val newVersion = TelegramClientVersionExtractor.extract(
                    directories,
                    TelegramClientPlatformType.MAIN_PLATFORM,
                    forkRoot
                )
                if (newVersion != oldVersion) {
                    println(
                        "✅ Fork updated: " +
                                "${oldVersion.getDisplayName()} → ${newVersion.getDisplayName()}"
                    )
                }
            } else {
                error(
                    """
                    🛑 Merge conflict!
                    🔧 Please resolve manually before re-running this task.
                    """.trimIndent()
                )
            }
        }
    }
}
