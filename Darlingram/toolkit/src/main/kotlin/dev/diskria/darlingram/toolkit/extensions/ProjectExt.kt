package dev.diskria.darlingram.toolkit.extensions

import dev.diskria.darlingram.Metadata
import dev.diskria.darlingram.toolkit.ProjectDirectories
import org.gradle.api.Project
import java.util.Properties

fun Project.isFork(): Boolean =
    rootProject.name == Metadata.FORK_NAME

fun Project.isUpstream(): Boolean =
    isFork().not()

fun Project.getLocalProperty(key: String): String? =
    Properties().apply {
        rootProject.directories().getForkLocalProperties().inputStream().use { load(it) }
    }.getProperty(key, null)

fun Project.directories(): ProjectDirectories =
    ProjectDirectories(
        rootDir.apply {
            if (isUpstream()) {
                resolve(Metadata.FORK_NAME)
            }
        }
    )
