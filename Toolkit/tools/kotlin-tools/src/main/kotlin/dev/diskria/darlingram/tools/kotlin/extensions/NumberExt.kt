package dev.diskria.darlingram.tools.kotlin.extensions

fun Int?.orZero(): Int =
    this ?: 0

fun Long?.orZero(): Long =
    this ?: 0

fun Long.unpackHighInt(): Int =
    (this ushr Int.SIZE_BITS).toInt()

fun Long.unpackLowInt(): Int =
    toInt()

fun Int.alignPadding(blockSize: Int): Int =
    (blockSize - (this % blockSize)) % blockSize

fun Boolean.toInt(): Int =
    if (this) 1 else 0
