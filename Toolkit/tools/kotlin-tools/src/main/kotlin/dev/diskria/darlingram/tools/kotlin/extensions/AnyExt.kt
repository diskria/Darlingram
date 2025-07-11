package dev.diskria.darlingram.tools.kotlin.extensions

inline fun <reified T> T.className(): String =
    T::class.simpleName ?: T::class.java.simpleName

inline fun <T> T.modify(block: T.() -> T): T =
    block()
