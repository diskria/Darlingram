package dev.diskria.darlingram.tools.kotlin.extensions

fun Int?.orZero(): Int =
    this ?: 0

fun Long?.orZero(): Long =
    this ?: 0

fun packIntsToLong(high: Int, low: Int): Long =
    (Integer.toUnsignedLong(high) shl Int.SIZE_BITS) or Integer.toUnsignedLong(low)

fun Long.unpackHighInt(): Int =
    (this ushr Int.SIZE_BITS).toInt()

fun Long.unpackLowInt(): Int =
    toInt()
