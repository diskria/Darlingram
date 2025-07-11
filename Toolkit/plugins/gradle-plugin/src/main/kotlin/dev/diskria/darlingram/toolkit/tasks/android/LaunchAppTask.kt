package dev.diskria.darlingram.toolkit.tasks.android

import dev.diskria.darlingram.Metadata
import dev.diskria.darlingram.toolkit.utils.ProjectDirectories
import dev.diskria.darlingram.toolkit.utils.gradle.extensions.getAndroidSdkShell
import dev.diskria.darlingram.toolkit.utils.gradle.extensions.getBuildDirectory
import dev.diskria.darlingram.toolkit.utils.kotlin.extensions.findLastModifiedFile
import dev.diskria.darlingram.toolkit.utils.kotlin.utils.Constants
import dev.diskria.darlingram.toolkit.utils.kotlin.utils.Shell
import java.io.File

@Suppress("unused")
abstract class LaunchAppTask : AndroidToolkitTask(
    "Launch app"
) {
    override fun runTask(directories: ProjectDirectories) {
        val apkFile = project.getBuildDirectory().findLastModifiedFile(Constants.File.Extension.APK)
            ?: error("APK not found")

        val androidSdkManager = project.getAndroidSdkShell() ?: error("Not android gradle module")
        val projectApplicationId = androidSdkManager.getApplicationId()
        val apkApplicationId = androidSdkManager.getApkApplicationId(apkFile)
        require(projectApplicationId == apkApplicationId) { "Application id mismatch!" }

        val deviceIds = androidSdkManager.getConnectedDevices()
        require(deviceIds.isNotEmpty()) { "No connected devices" }

        val apkBuildId = readFileFromZip(apkFile, GenerateBuildUuidTask.ASSETS_FILE_PATH)

        deviceIds.forEach { deviceId ->
            val deviceShell = androidSdkManager.getDeviceShell(deviceId)
            deviceShell.activityManager.forceStop(apkApplicationId)
            val deviceApkPath = deviceShell.packageManager.getApkPath(apkApplicationId)
            val deviceApkBuildId = deviceShell.runAndGetOutput(
                getReadFileFromZipCommand(
                    deviceApkPath,
                    GenerateBuildUuidTask.ASSETS_FILE_PATH
                )
            )
            if (apkBuildId == deviceApkBuildId) {
                deviceShell.activityManager.startActivity(
                    apkApplicationId,
                    deviceShell
                        .systemDumpManager
                        .getEnabledAppIconActivity(
                            apkApplicationId, Metadata.TELEGRAM_APP_ICON_COMPONENT_NAME_PREFIX,
                            Metadata.TELEGRAM_DEFAULT_APP_ICON_COMPONENT_NAME
                        )
                )
            }
        }
    }

    fun readFileFromZip(zipFile: File, internalPath: String): String =
        Shell.open().runAndGetOutput(
            getReadFileFromZipCommand(zipFile.absolutePath, internalPath)
        )

    fun getReadFileFromZipCommand(zipFilePath: String, internalPath: String): String =
        "unzip -p $zipFilePath $internalPath"

}
