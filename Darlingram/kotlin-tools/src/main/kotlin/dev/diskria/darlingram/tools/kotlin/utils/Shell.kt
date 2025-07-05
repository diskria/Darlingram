package dev.diskria.darlingram.tools.kotlin.utils

import dev.diskria.darlingram.tools.kotlin.extensions.toCharOrThrow
import java.io.File

class Shell(var workingDirectory: File = File(Constants.File.CURRENT_DIRECTORY)) {

    private var previousDirectory: File? = null

    fun cd(directory: File): Shell {
        previousDirectory = workingDirectory
        workingDirectory = directory
        return this
    }

    fun popd(): Shell {
        previousDirectory?.let {
            workingDirectory = it
            previousDirectory = null
        }
        return this
    }

    fun pwd(): File =
        workingDirectory

    fun run(command: String, quiet: Boolean = false): Boolean =
        runWithExitCode(command, quiet) == 0

    fun runWithExitCode(command: String, quiet: Boolean = false): Int =
        startProcess(command, quiet).waitFor()

    fun runWithOutput(command: String, quiet: Boolean = false): String =
        startProcess(command, quiet)
            .inputStream
            .bufferedReader()
            .readText()
            .trim()

    private fun startProcess(command: String, quiet: Boolean = false): Process =
        ProcessBuilder(splitToArguments(command))
            .directory(workingDirectory)
            .redirectErrorStream(true)
            .start()
            .apply {
                if (quiet) {
                    inputStream.close()
                    errorStream.close()
                }
            }

    private fun splitToArguments(command: String): List<String> =
        Regex("""(?:[^\s"']+|"[^"]*"|'[^']*')+""")
            .findAll(command)
            .map {
                it.value.trim(
                    Constants.Symbol.DOUBLE_QUOTE.toCharOrThrow(),
                    Constants.Symbol.SINGLE_QUOTE.toCharOrThrow()
                )
            }.toList()
}
