package dev.diskria.darlingram.toolkit.plugin.tasks

import dev.diskria.darlingram.Metadata
import dev.diskria.darlingram.toolkit.platforms.ClientVersionExtractor
import dev.diskria.darlingram.toolkit.platforms.PlatformType
import dev.diskria.darlingram.tools.kotlin.utils.Shell
import java.util.Locale

@Suppress("unused")
abstract class SyncUpstreamTask : GradleToolkitTask(
    "Merge upstream changes into fork"
) {
    override fun runTask() {
        val upstreamRoot = directories.getUpstreamRoot()
        val remoteName = Metadata.UPSTREAM_NAME.lowercase(Locale.ROOT)
        val remoteUrl = PlatformType.ANDROID.repositoryUrl
        val gitBranch = PlatformType.ANDROID.gitBranch
        val oldVersion = ClientVersionExtractor.extract(
            directories,
            PlatformType.ANDROID,
            upstreamRoot
        )
        val shell = Shell(upstreamRoot)
        if (shell.runWithOutput("git remote get-url $remoteName") != remoteUrl) {
            shell.run("git remote add $remoteName $remoteUrl")
        }
        shell.run("git fetch $remoteName")
        val aheadCount = shell.runWithOutput(
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
                val newVersion = ClientVersionExtractor.extract(
                    directories,
                    PlatformType.ANDROID,
                    upstreamRoot
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
