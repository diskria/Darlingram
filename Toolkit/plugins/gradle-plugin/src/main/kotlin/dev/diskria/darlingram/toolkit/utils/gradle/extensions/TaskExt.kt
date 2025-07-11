package dev.diskria.darlingram.toolkit.utils.gradle.extensions

import dev.diskria.darlingram.toolkit.ProjectDirectories
import org.gradle.api.DefaultTask
import kotlin.reflect.KClass

fun <T : DefaultTask> KClass<T>.getDisplayName(): String =
    simpleName
        ?.removeSuffix("Task")
        ?.replaceFirstChar { it.lowercaseChar() }
        ?: error("Cannot derive task name from class: $this")

fun DefaultTask.directories(): ProjectDirectories =
    project.rootProject.directories()
