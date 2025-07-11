package dev.diskria.darlingram.toolkit.utils.gradle.extensions

import com.android.build.gradle.AppExtension
import dev.diskria.darlingram.Metadata
import dev.diskria.darlingram.toolkit.utils.ProjectDirectories
import dev.diskria.darlingram.toolkit.utils.shell.AndroidSdkShell
import dev.diskria.darlingram.toolkit.utils.kotlin.extensions.equalsIgnoreCase
import dev.diskria.darlingram.toolkit.utils.kotlin.extensions.modifyIf
import dev.diskria.darlingram.toolkit.utils.kotlin.extensions.toTypedOrNull
import dev.diskria.darlingram.toolkit.utils.kotlin.extensions.tryCatch
import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.provider.Provider
import org.gradle.plugin.use.PluginDependency
import java.io.File
import java.util.Properties

fun Project.isTelegram(): Boolean =
    rootProject.name.equalsIgnoreCase(Metadata.TELEGRAM_NAME)

fun Project.getLocalProperty(key: String): String? =
    getPropertyOrNull(directories().getLocalProperties(), key)

inline fun <reified T> Project.getProperty(propertiesFile: File, key: String): T =
    getPropertyOrNull(propertiesFile, key)
        ?: error("Property $key not found in ${propertiesFile.absolutePath}")

inline fun <reified T> Project.getPropertyOrNull(propertiesFile: File, key: String): T? =
    Properties().apply {
        propertiesFile.inputStream().use { load(it) }
    }.getProperty(key, null)?.toTypedOrNull<T>()

fun Project.directories(): ProjectDirectories =
    ProjectDirectories(
        rootDir.modifyIf(isTelegram()) { resolve(Metadata.PROJECT_NAME) }
    )

fun Project.getBuildDirectory(): File =
    layout.buildDirectory.asFile.get()

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
