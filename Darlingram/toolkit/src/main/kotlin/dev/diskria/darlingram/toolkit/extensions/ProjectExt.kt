package dev.diskria.darlingram.toolkit.extensions

import dev.diskria.darlingram.Metadata
import dev.diskria.darlingram.toolkit.ProjectDirectories
import org.gradle.api.Project
import java.util.Properties

fun Project.isFork(): Boolean =
    rootProject.name == Metadata.FORK_NAME

fun Project.getForkLocalProperty(key: String): String? =
    Properties().apply {
        project.directories().getForkLocalProperties().inputStream().use { load(it) }
    }.getProperty(key, null)

fun Project.directories(): ProjectDirectories =
    ProjectDirectories(
        if (isFork()) rootProject.rootDir
        else rootProject.file(Metadata.FORK_NAME)
    )
