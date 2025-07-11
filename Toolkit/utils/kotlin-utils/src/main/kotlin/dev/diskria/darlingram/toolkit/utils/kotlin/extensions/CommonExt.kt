package dev.diskria.darlingram.toolkit.utils.kotlin.extensions

import java.util.UUID

inline fun <T> tryCatch(block: () -> T): T? =
    try {
        block()
    } catch (_: Exception) {
        null
    }

inline fun <T> tryCatch(defaultValue: T, block: () -> T): T =
    try {
        block()
    } catch (_: Exception) {
        defaultValue
    }

fun packIntsToLong(high: Int, low: Int): Long =
    (Integer.toUnsignedLong(high) shl Int.SIZE_BITS) or Integer.toUnsignedLong(low)

infix fun Int.downUntil(to: Int) = (this - 1).downTo(to)

fun generateUuid(): String = UUID.randomUUID().toString()
