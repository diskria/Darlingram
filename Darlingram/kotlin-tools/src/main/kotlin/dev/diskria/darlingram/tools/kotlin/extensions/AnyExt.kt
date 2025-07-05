package dev.diskria.darlingram.tools.kotlin.extensions

inline fun <reified T> T.className(): String =
    T::class.simpleName ?: T::class.java.simpleName
