package dev.diskria.darlingram.tools.kotlin.extensions

import java.io.InputStream

fun InputStream.readText(): String =
    this.bufferedReader()
        .readText()
