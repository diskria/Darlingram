package dev.diskria.darlingram.toolkit.extensions

import dev.diskria.darlingram.Metadata
import dev.diskria.darlingram.toolkit.ProjectDirectories
import org.gradle.api.Project
import java.util.Properties

fun Project.isTelegram(): Boolean =
    rootProject.name == Metadata.TELEGRAM_NAME

fun Project.getLocalProperty(key: String): String? =
    Properties().apply {
        rootProject.directories().getLocalProperties().inputStream().use { load(it) }
    }.getProperty(key, null)

fun Project.directories(): ProjectDirectories =
    ProjectDirectories(
        if (isTelegram()) rootDir.resolve(Metadata.PROJECT_NAME)
        else rootDir
    )
