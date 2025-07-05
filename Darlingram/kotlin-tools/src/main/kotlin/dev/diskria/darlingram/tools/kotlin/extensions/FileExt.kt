package dev.diskria.darlingram.tools.kotlin.extensions

import java.io.File

inline fun File.readByLines(crossinline lineReader: (String) -> Unit) =
    this.useLines { lines -> lines.forEach { line -> lineReader(line) } }
