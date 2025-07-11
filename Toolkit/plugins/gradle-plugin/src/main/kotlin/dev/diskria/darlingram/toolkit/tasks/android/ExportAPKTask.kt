package dev.diskria.darlingram.toolkit.tasks.android

import dev.diskria.darlingram.Metadata
import dev.diskria.darlingram.toolkit.utils.ProjectDirectories
import dev.diskria.darlingram.toolkit.utils.PropertyNames
import dev.diskria.darlingram.toolkit.utils.gradle.extensions.getAndroidSdkShell
import dev.diskria.darlingram.toolkit.utils.gradle.extensions.getBuildDirectory
import dev.diskria.darlingram.toolkit.utils.gradle.extensions.getLocalProperty
import dev.diskria.darlingram.toolkit.utils.gradle.extensions.isTelegram
import dev.diskria.darlingram.toolkit.utils.kotlin.extensions.fileName
import dev.diskria.darlingram.toolkit.utils.kotlin.extensions.findLastModifiedFile
import dev.diskria.darlingram.toolkit.utils.kotlin.extensions.format
import dev.diskria.darlingram.toolkit.utils.kotlin.extensions.takeIfExceeds
import dev.diskria.darlingram.toolkit.utils.kotlin.utils.Constants
import dev.diskria.darlingram.toolkit.utils.kotlin.utils.DateFormat
import java.util.Date

@Suppress("unused")
abstract class ExportAPKTask : AndroidToolkitTask(
    "Copies the latest built APK into the repo root",
) {
    override fun runTask(directories: ProjectDirectories) {
        val apkFile = project.getBuildDirectory().findLastModifiedFile(Constants.File.Extension.APK)
            ?: error("APK not found")

        val androidSdkShell = project.getAndroidSdkShell() ?: error("Not android gradle module")
        val projectApplicationId = androidSdkShell.getApplicationId()
        val apkApplicationId = androidSdkShell.getApkApplicationId(apkFile)

        require(projectApplicationId == apkApplicationId) { "Application id mismatch!" }

        val nowDate = Date()
        val date = nowDate.format(DateFormat.ISO_DATE)
        val time = nowDate.format(DateFormat.TIME_SECONDS)

        val apkFileName = fileName(
            listOf(
                if (project.isTelegram()) Metadata.TELEGRAM_NAME else Metadata.PROJECT_NAME,
                apkFile.parentFile.name,
                date,
                time
            ).joinToString(Constants.Symbol.UNDERSCORE),
            Constants.File.Extension.APK
        )
        val outputDirectory = directories.getExtractedAPKs(project.isTelegram())
        outputDirectory.mkdirs()

        val filesLimit = project
            .getLocalProperty(PropertyNames.APK_FILES_LIMIT)
            ?.toInt()
            ?: DEFAULT_FILES_LIMIT
        apkFile.copyTo(outputDirectory.resolve(apkFileName), overwrite = true)

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
