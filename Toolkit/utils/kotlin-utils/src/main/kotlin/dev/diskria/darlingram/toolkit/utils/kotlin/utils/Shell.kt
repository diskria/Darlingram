package dev.diskria.darlingram.toolkit.utils.kotlin.utils

import dev.diskria.darlingram.toolkit.utils.kotlin.extensions.asDirectoryOrThrow
import dev.diskria.darlingram.toolkit.utils.kotlin.extensions.asFileOrNull
import dev.diskria.darlingram.toolkit.utils.kotlin.extensions.modifyFirst
import dev.diskria.darlingram.toolkit.utils.kotlin.extensions.readText
import dev.diskria.darlingram.toolkit.utils.kotlin.extensions.toCharOrThrow
import dev.diskria.darlingram.toolkit.utils.kotlin.extensions.toFile
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
        runAndGetExitCode(command) == SUCCESS_EXIT_CODE

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

        if (workingDirectory.resolve(executable).asFileOrNull()?.canExecute() == true) {
            arguments.modifyFirst { Constants.File.Path.CURRENT_DIRECTORY + it }
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
        private const val SUCCESS_EXIT_CODE: Int = 0
        private const val ERROR_EXIT_CODE: Int = 1

        fun open(workingDirectoryPath: String = Constants.File.Path.CURRENT_DIRECTORY): Shell =
            Shell(workingDirectoryPath.toFile())

        fun open(workingDirectory: File): Shell =
            Shell(workingDirectory.asDirectoryOrThrow())
    }
}
