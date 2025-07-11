package dev.diskria.darlingram.toolkit.tasks.android

import dev.diskria.darlingram.toolkit.ProjectDirectories
import dev.diskria.darlingram.toolkit.utils.gradle.extensions.directories
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import java.io.File

sealed class AndroidToolkitTask(
    @Internal val taskDescription: String,
) : DefaultTask() {

    @Internal
    val buildDirectory: File =
        project.layout.buildDirectory.asFile.get()

    @Internal
    final override fun getDescription(): String =
        taskDescription

    @Internal
    final override fun getGroup(): String =
        "android-application"

    @Internal
    protected abstract fun runTask(directories: ProjectDirectories)

    @TaskAction
    private fun action() {
        runTask(project.directories())
    }
}
