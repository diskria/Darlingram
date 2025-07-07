package dev.diskria.darlingram.toolkit.plugin.tasks

import dev.diskria.darlingram.Metadata
import dev.diskria.darlingram.toolkit.extensions.getLocalProperty
import dev.diskria.darlingram.toolkit.extensions.isTelegram
import dev.diskria.darlingram.tools.kotlin.extensions.takeIfExceeds
import dev.diskria.darlingram.tools.kotlin.utils.Constants
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Suppress("unused")
abstract class ExportAPKTask : GradleToolkitTask(
    "Copies the latest built APK into the repo root"
) {
    override fun runTask() {
        val apkFile = buildDirectory
            .walkTopDown()
            .filter { it.isFile && it.extension == Constants.File.Extension.APK }
            .maxByOrNull { it.lastModified() }
            ?: error("APK not found")

        val buildType = apkFile.parentFile.name
        val sdfDate = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val sdfTime = SimpleDateFormat("HH:mm:ss", Locale.US)

        val now = Date()
        val date = sdfDate.format(now)
        val time = sdfTime.format(now)

        val apkName = listOf(
            if (project.isTelegram()) Metadata.TELEGRAM_NAME else Metadata.PROJECT_NAME,
            buildType,
            date,
            time
        ).joinToString(Constants.Symbol.UNDERSCORE) +
                Constants.Symbol.DOT +
                Constants.File.Extension.APK
        val outputDirectory = directories.getAPK(project.isTelegram())
        outputDirectory.mkdirs()

        val filesLimit = rootProject
            .getLocalProperty("APK_FILES_LIMIT")
            ?.toInt()
            ?: DEFAULT_FILES_LIMIT
        apkFile.copyTo(outputDirectory.resolve(apkName), overwrite = true)

        val apkFiles = outputDirectory
            .listFiles { file -> file.isFile && file.extension == Constants.File.Extension.APK }
            ?.sortedBy { it.lastModified() }
            .orEmpty()

        apkFiles.takeIfExceeds(filesLimit)?.forEach { it.delete() }
    }

    companion object {
        private const val DEFAULT_FILES_LIMIT = 5
    }
}
