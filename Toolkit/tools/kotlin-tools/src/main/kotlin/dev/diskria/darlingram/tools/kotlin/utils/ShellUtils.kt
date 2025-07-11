package dev.diskria.darlingram.tools.kotlin.utils

import java.io.File

object ShellUtils {

    fun readFileFromZip(zipFile: File, internalPath: String): String =
        Shell.open().runAndGetOutput(
            getReadFileFromZipCommand(zipFile.absolutePath, internalPath)
        )

    fun getReadFileFromZipCommand(zipFilePath: String, internalPath: String): String =
        "unzip -p $zipFilePath $internalPath"
}
