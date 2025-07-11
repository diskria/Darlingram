package dev.diskria.darlingram.toolkit.tasks.android

import dev.diskria.darlingram.Metadata
import dev.diskria.darlingram.toolkit.ProjectDirectories
import dev.diskria.darlingram.toolkit.utils.gradle.extensions.getAndroidSdkShell
import dev.diskria.darlingram.tools.kotlin.extensions.findLastModifiedFile
import dev.diskria.darlingram.tools.kotlin.utils.Constants
import dev.diskria.darlingram.tools.kotlin.utils.ShellUtils

@Suppress("unused")
abstract class LaunchAppTask : AndroidToolkitTask(
    "Launch app"
) {
    override fun runTask(directories: ProjectDirectories) {
        val apkFile = buildDirectory.findLastModifiedFile(Constants.File.Extension.APK)
            ?: error("APK not found")

        val androidSdkManager = project.getAndroidSdkShell() ?: error("Not android gradle module")
        val projectApplicationId = androidSdkManager.getApplicationId()
        val apkApplicationId = androidSdkManager.getApkApplicationId(apkFile)
        require(projectApplicationId == apkApplicationId) { "Application id mismatch!" }

        val deviceIds = androidSdkManager.getConnectedDevices()
        require(deviceIds.isNotEmpty()) { "No connected devices" }

        val apkBuildId = ShellUtils.readFileFromZip(apkFile, GenerateBuildUuidTask.ASSETS_FILE_PATH)

        deviceIds.forEach { deviceId ->
            val deviceShell = androidSdkManager.getDeviceShell(deviceId)
            deviceShell.activityManager.forceStop(apkApplicationId)
            val deviceApkPath = deviceShell.packageManager.getApkPath(apkApplicationId)
            val deviceApkBuildId = deviceShell.runAndGetOutput(
                ShellUtils.getReadFileFromZipCommand(
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
}
