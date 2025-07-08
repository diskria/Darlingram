package dev.diskria.darlingram.api.models.extensions

import dev.diskria.darlingram.api.models.common.TLArray
import dev.diskria.darlingram.api.models.common.TLConstructor
import dev.diskria.darlingram.api.models.common.TLObject
import dev.diskria.darlingram.api.models.primitive.TLBoolean
import dev.diskria.darlingram.api.models.primitive.TLBooleanTrue
import dev.diskria.darlingram.api.models.primitive.TLByte
import dev.diskria.darlingram.api.models.primitive.TLByteArray
import dev.diskria.darlingram.api.models.primitive.TLDouble
import dev.diskria.darlingram.api.models.primitive.TLFloat
import dev.diskria.darlingram.api.models.primitive.TLInt
import dev.diskria.darlingram.api.models.primitive.TLLong
import dev.diskria.darlingram.api.models.primitive.TLString
import dev.diskria.darlingram.tools.kotlin.extensions.downUntil
import dev.diskria.darlingram.tools.kotlin.extensions.unsupportedValue
import java.nio.ByteOrder

fun Int.toTLConstructor(): TLConstructor = TLConstructor(this)

fun Long.toTLConstructor(): TLConstructor = toInt().toTLConstructor()

fun TLByte.mapToInt(): Int = toRaw().toInt() and 0xFF

fun TLInt.mapToByte(): Byte = toRaw().toByte()

fun TLInt.mapToFloat(): Float = Float.fromBits(toRaw())

fun TLFloat.mapToInt(): Int = toRaw().toBits()

fun TLLong.mapToInt(): Int = toRaw().toInt()

fun TLLong.mapToDouble(): Double = Double.fromBits(toRaw())

fun TLDouble.mapToLong(): Long = toRaw().toRawBits()

fun TLBoolean.mapToInt(): Int = getConstructor().toRaw()

fun TLInt.mapToBoolean(): Boolean = getConstructor() == TLBooleanTrue.CONSTRUCTOR

fun TLString.mapToByteArray(): ByteArray = toRaw().toByteArray()

fun TLByteArray.mapToString(): String = toRaw().toString()

fun Byte.toTLByte(): TLByte =
    TLByte(this)

fun Int.toTLInt(): TLInt =
    TLInt(this)

fun Long.toTLLong(): TLLong =
    TLLong(this)

fun Float.toTLFloat(): TLFloat =
    TLFloat(this)

fun Double.toTLDouble(): TLDouble =
    TLDouble(this)

fun Boolean.toTLBoolean(): TLBoolean =
    TLBoolean.fromRaw(this)

fun String.toTLString(): TLString =
    TLString(this)

fun ByteArray.toTLByteArray(): TLByteArray =
    TLByteArray(this)

fun <T : TLObject> List<T>.toTLArray(): TLArray<T> =
    TLArray(this)

inline fun encodeIntBytes(value: Int, order: ByteOrder, writer: (Int) -> Unit) {
    encodeBytes(Int.SIZE_BYTES, value.toLong(), order, writer)
}

inline fun encodeLongBytes(value: Long, order: ByteOrder, writer: (Int) -> Unit) {
    encodeBytes(Long.SIZE_BYTES, value, order, writer)
}

inline fun encodeBytes(count: Int, value: Long, order: ByteOrder, writer: (Int) -> Unit) {
    val range = when (order) {
        ByteOrder.LITTLE_ENDIAN -> 0 until count
        ByteOrder.BIG_ENDIAN -> count downUntil 0
        else -> unsupportedValue(order)
    }
    range.forEach { multiplier ->
        val shift = multiplier * Byte.SIZE_BITS
        writer(value.shr(shift).toInt())
    }
}

inline fun decodeIntBytes(order: ByteOrder, reader: () -> Int): Int =
    decodeBytes(Int.SIZE_BYTES, order, reader).toInt()

inline fun decodeLongBytes(order: ByteOrder, reader: () -> Int): Long =
    decodeBytes(Long.SIZE_BYTES, order, reader)

inline fun decodeBytes(count: Int, order: ByteOrder, reader: () -> Int): Long {
    val range = when (order) {
        ByteOrder.LITTLE_ENDIAN -> 0 until count
        ByteOrder.BIG_ENDIAN -> count downUntil 0
        else -> unsupportedValue(order)
    }
    var result = 0L
    range.forEach { multiplier ->
        val shift = multiplier * Byte.SIZE_BITS
        result = result or reader().toLong().shl(shift)
    }
    return result
}
