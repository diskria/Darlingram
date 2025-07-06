package dev.diskria.darlingram.tools.kotlin.extensions

inline fun <T> tryCatch(block: () -> T): T? =
    try {
        block()
    } catch (_: Exception) {
        null
    }

inline fun <T> tryCatch(default: T, block: () -> T): T =
    try {
        block()
    } catch (_: Exception) {
        default
    }

fun packIntsToLong(high: Int, low: Int): Long =
    (Integer.toUnsignedLong(high) shl Int.SIZE_BITS) or Integer.toUnsignedLong(low)
