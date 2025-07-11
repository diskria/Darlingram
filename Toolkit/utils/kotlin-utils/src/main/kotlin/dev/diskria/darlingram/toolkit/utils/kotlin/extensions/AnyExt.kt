package dev.diskria.darlingram.toolkit.utils.kotlin.extensions

inline fun <reified T> T.className(): String =
    T::class.simpleName ?: T::class.java.simpleName

inline fun <T> T.modify(block: T.() -> T): T =
    block()

inline fun <T> T.modifyIf(condition: Boolean, block: T.() -> T): T =
    if (condition) block() else this
