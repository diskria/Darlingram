package dev.diskria.darlingram.toolkit.utils.kotlin.extensions

import java.io.InputStream

fun InputStream.readText(): String =
    this.bufferedReader()
        .readText()
