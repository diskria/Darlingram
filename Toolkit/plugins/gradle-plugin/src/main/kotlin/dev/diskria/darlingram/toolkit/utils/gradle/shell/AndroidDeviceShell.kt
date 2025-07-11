package dev.diskria.darlingram.toolkit.utils.gradle.shell

import dev.diskria.darlingram.tools.kotlin.utils.Shell
import java.io.File

class AndroidDeviceShell(
    platformToolsDirectory: File,
    private val deviceId: String,
) {
    val activityManager: ActivityManager by lazy { ActivityManager() }
    val packageManager: PackageManager by lazy { PackageManager() }
    val systemDumpManager: SystemDumpManager by lazy { SystemDumpManager() }

    private val shell: Shell = Shell.Companion.open(platformToolsDirectory)

    fun runAndGetOutput(command: String): String =
        shell.runAndGetOutput(command)

    fun runAndGetExitCode(command: String): Int =
        shell.runAndGetExitCode(command)

    fun run(command: String): Boolean =
        shell.run(command)

    inner class ActivityManager() : Command(shell, deviceId, "am") {

        fun startActivity(applicationId: String, activityName: String) {
            execute("start -n $applicationId/$activityName")
        }

        fun forceStop(packageName: String) {
            execute("force-stop $packageName")
        }
    }

    inner class PackageManager() : Command(shell, deviceId, "pm") {

        fun getApkPath(applicationId: String): String =
            execute("path $applicationId").removePrefix("package:")
    }

    inner class SystemDumpManager() : Command(shell, deviceId, "dumpsys") {

        fun getEnabledAppIconActivity(
            applicationId: String,
            prefix: String,
            defaultIcon: String,
        ): String =
            execute("package $applicationId")
                .lineSequence()
                .dropWhile { !it.contains("enabledComponents") }
                .drop(1)
                .map { it.trim() }
                .firstOrNull { it.startsWith(prefix) }
                ?: (prefix + defaultIcon)
    }

    sealed class Command(
        val shell: Shell,
        val deviceId: String,
        val prefix: String,
    ) {
        fun execute(command: String): String =
            shell.runAndGetOutput("adb -s $deviceId shell $prefix $command")
    }
}