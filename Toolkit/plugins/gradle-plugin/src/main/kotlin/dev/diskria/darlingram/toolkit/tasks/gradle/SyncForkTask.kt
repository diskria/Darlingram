package dev.diskria.darlingram.toolkit.tasks.gradle

import dev.diskria.darlingram.toolkit.submodules.PlatformType
import dev.diskria.darlingram.toolkit.submodules.TelegramClients
import dev.diskria.darlingram.toolkit.utils.ProjectDirectories
import dev.diskria.darlingram.toolkit.utils.shell.GitShell

@Suppress("unused")
abstract class SyncForkTask : GradleToolkitTask(
    "Merge upstream changes into fork"
) {
    override fun runTask(directories: ProjectDirectories) {
        val forkRoot = directories.getForkRoot()
        val remoteUrl = PlatformType.MAIN_PLATFORM.getRepositoryUrl()
        val gitBranch = PlatformType.MAIN_PLATFORM.gitBranch
        val oldVersion = TelegramClients.extractClientVersion(
            this,
            PlatformType.MAIN_PLATFORM,
            forkRoot
        )
        val gitShell = GitShell.open(forkRoot)
        if (gitShell.getRemoteUrl(GitShell.UPSTREAM_REMOTE_NAME) != remoteUrl) {
            gitShell.addRemote(GitShell.UPSTREAM_REMOTE_NAME, remoteUrl)
        }
        gitShell.fetch(GitShell.UPSTREAM_REMOTE_NAME)
        val aheadCount = gitShell.getAheadCount(GitShell.UPSTREAM_REMOTE_NAME, gitBranch)
        if (aheadCount == 0) {
            println(
                "ℹ\uFE0F Fork already up-to-date, current version: ${oldVersion.getDisplayName()}"
            )
            return
        }
        if (gitShell.hasUncommitedChanges()) {
            error(
                """
                🛑 Uncommited changes detected!
                💡 You must commit your changes before updating from upstream.
                """.trimIndent()
            )
        }
        val hasMergeConflicts = gitShell.merge(GitShell.UPSTREAM_REMOTE_NAME, gitBranch).not()
        if (hasMergeConflicts) {
            error(
                """
                🛑 Merge conflicts!
                🔧 Please resolve manually before re-running this task.
                """.trimIndent()
            )
        }
        val newVersion = TelegramClients.extractClientVersion(
            this,
            PlatformType.MAIN_PLATFORM,
            forkRoot
        )
        if (newVersion != oldVersion) {
            println(
                "✅ Fork updated: " +
                        "${oldVersion.getDisplayName()} → ${newVersion.getDisplayName()}"
            )
        }
    }
}
