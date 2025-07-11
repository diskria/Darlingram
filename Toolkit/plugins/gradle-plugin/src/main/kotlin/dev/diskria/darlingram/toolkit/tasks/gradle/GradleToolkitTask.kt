package dev.diskria.darlingram.toolkit.tasks.gradle

import dev.diskria.darlingram.toolkit.ProjectDirectories
import dev.diskria.darlingram.toolkit.utils.gradle.extensions.directories
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction

sealed class GradleToolkitTask(
    @Internal val taskDescription: String,
) : DefaultTask() {

    @Internal
    private val directories: ProjectDirectories =
        project.rootProject.directories()

    @Internal
    final override fun getDescription(): String =
        taskDescription

    @Internal
    final override fun getGroup(): String =
        "gradle-toolkit"

    @Internal
    protected abstract fun runTask(directories: ProjectDirectories)

    @TaskAction
    private fun action() {
        runTask(directories)
    }
}
