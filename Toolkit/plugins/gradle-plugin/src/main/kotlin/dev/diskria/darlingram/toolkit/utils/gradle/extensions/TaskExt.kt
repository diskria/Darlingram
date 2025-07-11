package dev.diskria.darlingram.toolkit.utils.gradle.extensions

import dev.diskria.darlingram.toolkit.tasks.ToolkitTask
import dev.diskria.darlingram.toolkit.utils.ProjectDirectories
import org.gradle.api.DefaultTask
import kotlin.reflect.KClass

fun <T : ToolkitTask> KClass<T>.getDisplayName(): String =
    simpleName
        ?.removeSuffix("Task")
        ?.replaceFirstChar { it.lowercaseChar() }
        ?: error("Cannot derive task name from class: $this")

fun DefaultTask.directories(): ProjectDirectories =
    project.directories()
