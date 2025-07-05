package dev.diskria.darlingram.toolkit.plugin.tasks

import dev.diskria.darlingram.toolkit.ProjectDirectories
import dev.diskria.darlingram.toolkit.extensions.directories
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import java.io.File
import kotlin.reflect.KClass

/**
 * Base class for toolkit Gradle tasks.
 *
 * All subclasses **must be declared abstract** in order for Gradle to register them correctly via Kotlin DSL.
 *
 */
sealed class GradleToolkitTask(@Internal val taskDescription: String) : DefaultTask() {

    @Internal
    val directories: ProjectDirectories = project.directories()

    @Internal
    final override fun getDescription(): String = taskDescription

    @Internal
    final override fun getGroup(): String = "toolkit"

    @TaskAction
    private fun action() {
        runTask()
    }

    @Internal
    protected abstract fun runTask()

    @Internal
    fun buildDir(): File =
        project.layout.buildDirectory.asFile.get()
}

@Internal
fun <T : GradleToolkitTask> KClass<T>.getDisplayName(): String =
    simpleName
        ?.removeSuffix("Task")
        ?.replaceFirstChar { it.lowercaseChar() }
        ?: error("Cannot derive task name from class: $this")
