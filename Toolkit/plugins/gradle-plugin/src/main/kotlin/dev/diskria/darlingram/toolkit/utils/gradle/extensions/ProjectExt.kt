package dev.diskria.darlingram.toolkit.utils.gradle.extensions

import com.android.build.gradle.AppExtension
import dev.diskria.darlingram.Metadata
import dev.diskria.darlingram.toolkit.ProjectDirectories
import dev.diskria.darlingram.toolkit.utils.gradle.shell.AndroidSdkShell
import dev.diskria.darlingram.tools.kotlin.extensions.equalsIgnoreCase
import dev.diskria.darlingram.tools.kotlin.extensions.tryCatch
import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.provider.Provider
import org.gradle.plugin.use.PluginDependency
import java.util.Properties

fun Project.isTelegram(): Boolean =
    rootProject.name.equalsIgnoreCase(Metadata.TELEGRAM_NAME)

fun Project.getLocalProperty(key: String): String? =
    Properties().apply {
        rootProject.directories().getLocalProperties().inputStream().use { load(it) }
    }.getProperty(key, null)

fun Project.directories(): ProjectDirectories =
    ProjectDirectories(
        if (isTelegram()) rootDir.resolve(Metadata.PROJECT_NAME)
        else rootDir
    )

fun Project.getAndroidSdkShell(): AndroidSdkShell? =
    getExtension<AppExtension>()?.let { AndroidSdkShell(it) }

fun Project.getCatalogLibrary(
    catalogName: String,
    alias: String,
): Provider<MinimalExternalModuleDependency> =
    getExtension<VersionCatalogsExtension>()
        ?.named(catalogName)
        ?.findLibrary(alias)
        ?.get()
        ?: error("Version catalogs not found")

fun Project.getCatalogPlugin(
    catalogName: String,
    alias: String,
): Provider<PluginDependency> =
    getExtension<VersionCatalogsExtension>()
        ?.named(catalogName)
        ?.findPlugin(alias)
        ?.get()
        ?: error("Version catalogs not found")

inline fun <reified T : Any> Project.getExtension(): T? =
    tryCatch {
        getExtensionOrThrow<T>()
    }

inline fun <reified T : Any> Project.getExtensionOrThrow(): T =
    extensions.getByType(T::class.java)
