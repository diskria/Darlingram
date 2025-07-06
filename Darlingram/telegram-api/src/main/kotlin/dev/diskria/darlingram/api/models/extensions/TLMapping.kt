package dev.diskria.darlingram.api.models.extensions

import dev.diskria.darlingram.api.models.common.TLConstructor
import dev.diskria.darlingram.api.models.common.TLObject
import dev.diskria.darlingram.api.models.primitive.TLArray
import dev.diskria.darlingram.api.models.primitive.TLBoolean
import dev.diskria.darlingram.api.models.primitive.TLByte
import dev.diskria.darlingram.api.models.primitive.TLByteArray
import dev.diskria.darlingram.api.models.primitive.TLDouble
import dev.diskria.darlingram.api.models.primitive.TLFloat
import dev.diskria.darlingram.api.models.primitive.TLInt
import dev.diskria.darlingram.api.models.primitive.TLLong
import dev.diskria.darlingram.api.models.primitive.TLString

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

fun TLInt.mapToBoolean(): Boolean = getConstructor() == TLBoolean.TRUE_CONSTRUCTOR

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
