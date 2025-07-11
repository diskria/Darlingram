package dev.diskria.darlingram.tools.kotlin.utils

import dev.diskria.darlingram.tools.kotlin.extensions.asDirectoryOrThrow
import dev.diskria.darlingram.tools.kotlin.extensions.modifyElement
import dev.diskria.darlingram.tools.kotlin.extensions.readText
import dev.diskria.darlingram.tools.kotlin.extensions.toCharOrThrow
import dev.diskria.darlingram.tools.kotlin.extensions.toFile
import java.io.File

class Shell private constructor(var workingDirectory: File) {

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

    fun run(command: String): Boolean =
        runAndGetExitCode(command) == 0

    fun runAndGetExitCode(command: String): Int =
        startProcess(command).waitFor()

    fun runAndGetOutput(command: String): String =
        startProcess(command)
            .inputStream
            .readText()
            .trim()

    private fun startProcess(command: String): Process {
        val arguments = splitToArguments(command).toMutableList()
        val executable = arguments.firstOrNull() ?: error("Empty command")

        val executableFile = workingDirectory.resolve(executable)
        if (executableFile.exists() && executableFile.canExecute()) {
            arguments.modifyElement(0) { "./$it" }
        }

        return ProcessBuilder(arguments)
            .directory(workingDirectory)
            .redirectErrorStream(true)
            .start()
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

    companion object {
        fun open(workingDirectoryPath: String = Constants.File.CURRENT_DIRECTORY): Shell =
            Shell(workingDirectoryPath.toFile())

        fun open(workingDirectory: File): Shell =
            Shell(workingDirectory.asDirectoryOrThrow())
    }
}
