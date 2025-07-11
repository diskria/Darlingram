package dev.diskria.darlingram.toolkit.utils.shell

import com.android.build.gradle.AppExtension
import dev.diskria.darlingram.toolkit.utils.kotlin.utils.Shell
import java.io.File

class AndroidSdkShell(val appExtension: AppExtension) {

    private val sdkDirectory = appExtension.sdkDirectory
    private val commandLineToolsDirectory: File = sdkDirectory.resolve("cmdline-tools/latest/bin")
    private val platformToolsDirectory: File = sdkDirectory.resolve("platform-tools")

    fun getApplicationId(): String =
        appExtension.defaultConfig.applicationId ?: error("Application id not found")

    fun getApkApplicationId(apkFile: File): String =
        Shell.open(commandLineToolsDirectory)
            .runAndGetOutput("apkanalyzer manifest application-id ${apkFile.absolutePath}")

    fun getConnectedDevices(): List<String> =
        Shell.open(platformToolsDirectory)
            .runAndGetOutput("adb devices")
            .lineSequence()
            .drop(1)
            .map { it.trim() }
            .filter { it.endsWith("device") }
            .map { it.substringBefore("\t") }
            .toList()

    fun getDeviceShell(deviceId: String): AdbShell =
        AdbShell(platformToolsDirectory, deviceId)
}