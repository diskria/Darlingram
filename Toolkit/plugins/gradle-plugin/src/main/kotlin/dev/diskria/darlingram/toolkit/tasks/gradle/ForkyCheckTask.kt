package dev.diskria.darlingram.toolkit.tasks.gradle

import dev.diskria.darlingram.toolkit.ProjectDirectories
import dev.diskria.darlingram.toolkit.platforms.TelegramClientPlatformType
import dev.diskria.darlingram.toolkit.platforms.TelegramClientVersionExtractor
import dev.diskria.darlingram.toolkit.utils.gradle.extensions.directories
import dev.diskria.darlingram.toolkit.utils.gradle.extensions.getDisplayName

@Suppress("unused")
abstract class ForkyCheckTask : GradleToolkitTask(
    "Forky!",
) {
    override fun runTask(directories: ProjectDirectories) {
        val directories = directories()

        val forkRoot = directories.getForkRoot()
        val forkVersion = TelegramClientVersionExtractor.extract(
            directories,
            TelegramClientPlatformType.MAIN_PLATFORM,
            forkRoot
        )
        val upstreamRoot = directories.getUpstreamRoot()
        val upstreamVersion = TelegramClientVersionExtractor.extract(
            directories,
            TelegramClientPlatformType.MAIN_PLATFORM,
            upstreamRoot
        )
        require(forkVersion == upstreamVersion) {
            """
            🛑 Version mismatch between fork and upstream!
            
            ┌──────────────────────┐
            │  Fork version:       ${forkVersion.getDisplayName()}
            │  Upstream version:   ${upstreamVersion.getDisplayName()}
            └──────────────────────┘
            
            💡 Your fork and upstream are out of sync.
               You may need to run:
                 • `./gradlew ${SyncForkTask::class.getDisplayName()}` – to sync fork
                 • `./gradlew ${UpdateUpstreamTask::class.getDisplayName()}` – to update upstream
               Or check manually to resolve any inconsistencies.
            """.trimIndent()
        }
        /* TODO
        1. Iterate through all upstream files and find corresponding files in the fork.
           - If a corresponding file is not found in the fork, register it as an error.

        2. Preprocess fork files by replacing or adding special markers to each injected code block.
           - Each injected block must start and end with a predefined special character sequence.

        3. Traverse both the upstream and fork file contents character by character in parallel:
           - When a start-of-block marker is detected in the fork, pause upstream reading.
           - Skip characters in the fork until the end-of-block marker is reached.
           - Resume upstream reading after the end-of-block marker.

        4. If any character differs during synchronized traversal (outside marked blocks),
           - This may indicate deleted upstream code or unmarked fork changes.
           - Record the line and column number of the mismatch.
           - Skip further validation for this file and mark it as problematic.

        5. At the end of the process, report all collected errors along with references to the problematic code.

        6. Implement support for ignored files:
           - Maintain a collection of file paths that should be excluded from comparison.
        */
    }
}
