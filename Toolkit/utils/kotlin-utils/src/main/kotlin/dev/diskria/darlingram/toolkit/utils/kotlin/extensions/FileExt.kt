package dev.diskria.darlingram.toolkit.utils.kotlin.extensions

import java.io.File

inline fun File.readByLines(crossinline lineReader: (String) -> Unit) =
    useLines { lines ->
        lines.forEach { line ->
            lineReader(line)
        }
    }

fun File.findLastModifiedFile(extension: String): File? =
    walkTopDown()
        .filter { it.isFile && it.extension.equalsIgnoreCase(extension) }
        .maxByOrNull { it.lastModified() }

fun File.asFileOrNull(): File? =
    takeIf { isFile }

fun File.asDirectoryOrNull(): File? =
    takeIf { isDirectory }

fun File.asFileOrThrow(): File =
    asFileOrNull() ?: error("Expected file, got: $this (exists=${exists()})")

fun File.asDirectoryOrThrow(): File =
    asDirectoryOrNull() ?: error("Expected directory, got: $this (exists=${exists()})")
