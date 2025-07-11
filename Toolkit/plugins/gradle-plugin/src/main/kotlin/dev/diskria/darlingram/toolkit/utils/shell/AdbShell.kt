package dev.diskria.darlingram.toolkit.utils.shell

import dev.diskria.darlingram.toolkit.utils.kotlin.utils.Shell
import java.io.File

class AdbShell(
    platformToolsDirectory: File,
    deviceId: String,
) {
    val activityManager: ActivityManager by lazy { ActivityManager() }
    val packageManager: PackageManager by lazy { PackageManager() }
    val systemDumpManager: SystemDumpManager by lazy { SystemDumpManager() }

    private val shell: Shell = Shell.open(platformToolsDirectory)
    private val baseCommand: String = "adb -s $deviceId shell"

    fun runAndGetOutput(command: String): String =
        shell.runAndGetOutput("$baseCommand $command")

    inner class ActivityManager() : Command("am") {

        fun startActivity(applicationId: String, activityName: String) {
            execute("start -n $applicationId/$activityName")
        }

        fun forceStop(packageName: String) {
            execute("force-stop $packageName")
        }
    }

    inner class PackageManager() : Command("pm") {

        fun getApkPath(applicationId: String): String =
            execute("path $applicationId").removePrefix("package:")
    }

    inner class SystemDumpManager() : Command("dumpsys") {

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

    open inner class Command(private val prefix: String) {

        fun execute(command: String): String =
            shell.runAndGetOutput("$baseCommand $prefix $command")
    }
}
