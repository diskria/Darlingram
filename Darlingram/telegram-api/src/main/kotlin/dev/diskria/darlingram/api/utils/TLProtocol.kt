package dev.diskria.darlingram.api.utils

import dev.diskria.darlingram.api.models.common.TLObject
import dev.diskria.darlingram.api.models.common.TLPrimitive
import dev.diskria.darlingram.api.models.extensions.decodeBytes
import dev.diskria.darlingram.api.models.extensions.encodeBytes
import dev.diskria.darlingram.api.models.extensions.mapToBoolean
import dev.diskria.darlingram.api.models.extensions.mapToByte
import dev.diskria.darlingram.api.models.extensions.mapToByteArray
import dev.diskria.darlingram.api.models.extensions.mapToDouble
import dev.diskria.darlingram.api.models.extensions.mapToFloat
import dev.diskria.darlingram.api.models.extensions.mapToInt
import dev.diskria.darlingram.api.models.extensions.mapToLong
import dev.diskria.darlingram.api.models.extensions.toTLBoolean
import dev.diskria.darlingram.api.models.extensions.toTLByte
import dev.diskria.darlingram.api.models.extensions.toTLByteArray
import dev.diskria.darlingram.api.models.extensions.toTLDouble
import dev.diskria.darlingram.api.models.extensions.toTLFloat
import dev.diskria.darlingram.api.models.extensions.toTLInt
import dev.diskria.darlingram.api.models.extensions.toTLLong
import dev.diskria.darlingram.api.models.extensions.toTLString
import dev.diskria.darlingram.api.models.primitive.TLBoolean
import dev.diskria.darlingram.api.models.primitive.TLByte
import dev.diskria.darlingram.api.models.primitive.TLByteArray
import dev.diskria.darlingram.api.models.primitive.TLDouble
import dev.diskria.darlingram.api.models.primitive.TLFloat
import dev.diskria.darlingram.api.models.primitive.TLInt
import dev.diskria.darlingram.api.models.primitive.TLLong
import dev.diskria.darlingram.api.models.primitive.TLString
import dev.diskria.darlingram.tools.kotlin.extensions.alignPadding
import dev.diskria.darlingram.tools.kotlin.extensions.tryCatch
import dev.diskria.darlingram.tools.kotlin.extensions.unsupportedType
import dev.diskria.darlingram.tools.kotlin.extensions.unsupportedValue
import java.nio.ByteOrder

abstract class TLProtocol {

    abstract fun getLength(): Int

    abstract fun getPosition(): Int

    abstract fun remaining(): Int

    abstract fun cleanup()

    abstract fun toByteArray(): ByteArray

    abstract fun skip(length: Int)

    abstract fun writeByte(value: TLByte)

    abstract fun readByte(defaultValue: TLByte? = null): TLByte

    abstract fun writeInt(value: TLInt)

    abstract fun readInt(defaultValue: TLInt? = null): TLInt

    abstract fun writeLong(value: TLLong)

    abstract fun readLong(defaultValue: TLLong? = null): TLLong

    abstract fun writeBytes(value: TLByteArray, offset: Int = 0, length: Int = value.size)

    abstract fun readBytes(output: TLByteArray, offset: Int = 0, length: Int = output.size)

    fun writeBoolean(value: TLBoolean) {
        writeInt(value.mapToInt().toTLInt())
    }

    fun readBoolean(defaultValue: TLBoolean? = null): TLBoolean =
        tryCatch(defaultValue) {
            readInt(defaultValue?.mapToInt()?.toTLInt()).mapToBoolean().toTLBoolean()
        } ?: error("Cannot read Boolean")

    fun writeFloat(value: TLFloat) {
        writeInt(value.mapToInt().toTLInt())
    }

    fun readFloat(defaultValue: TLFloat? = null): TLFloat =
        tryCatch(defaultValue) {
            readInt(defaultValue?.mapToInt()?.toTLInt()).mapToFloat().toTLFloat()
        } ?: error("Cannot read Float")

    fun writeDouble(value: TLDouble) {
        writeLong(value.mapToLong().toTLLong())
    }

    fun readDouble(defaultValue: TLDouble? = null): TLDouble =
        tryCatch(defaultValue) {
            readLong(defaultValue?.mapToLong()?.toTLLong()).mapToDouble().toTLDouble()
        } ?: error("Cannot read Double")

    fun writeString(value: TLString) {
        writeByteArray(value.mapToByteArray().toTLByteArray())
    }

    fun readString(defaultValue: TLString?): TLString =
        tryCatch(defaultValue) {
            readByteArray(defaultValue?.mapToByteArray()?.toTLByteArray()).toString().toTLString()
        } ?: error("Cannot read String")

    fun writeIntAsByte(value: TLInt) {
        writeByte(value.mapToByte().toTLByte())
    }

    fun readByteAsInt(): TLInt =
        readByte().mapToInt().toTLInt()

    fun writeByteArray(value: TLByteArray, offset: Int = 0, length: Int = value.size) {
        val headerLength = writeHeader(length)
        writeBytes(value, offset, length)
        writePadding(headerLength + length)
    }

    fun readByteArray(defaultValue: TLByteArray? = null): TLByteArray =
        readData { dataLength -> readData(dataLength, defaultValue) }

    fun writeData(dataSizeProvider: () -> Int, dataWriter: () -> Unit) {
        val dataSize = dataSizeProvider()
        val headerLength = writeHeader(dataSize)
        dataWriter()
        writePadding(headerLength + dataSize)
    }

    inline fun <T : TLObject> readData(defaultValue: T? = null, dataReader: (Int) -> T): T =
        tryCatch(defaultValue) {
            readHeader { headerLength, dataLength ->
                val data = dataReader(dataLength)
                skipPadding(headerLength, dataLength)
                return data
            }
        } ?: error("Cannot read data")

    fun readData(length: Int, defaultValue: TLByteArray? = null) =
        ByteArray(length).toTLByteArray().apply {
            tryCatch {
                readBytes(this)
            } ?: defaultValue?.run {
                tryCatch(defaultValue) {
                    readBytes(defaultValue)
                }
            } ?: error("Cannot read data")
        }

    inline fun <reified T : TLPrimitive<*>> read(defaultValue: T? = null): T =
        when (T::class) {
            TLByte::class -> readByte(defaultValue as TLByte)
            TLInt::class -> readInt(defaultValue as TLInt)
            TLLong::class -> readLong(defaultValue as TLLong)
            TLFloat::class -> readFloat(defaultValue as TLFloat)
            TLDouble::class -> readDouble(defaultValue as TLDouble)
            TLBoolean::class -> readBoolean(defaultValue as TLBoolean)
            TLString::class -> readString(defaultValue as TLString)
            TLByteArray::class -> readByteArray(defaultValue as TLByteArray)
            else -> unsupportedType(T::class)
        } as T

    inline fun <reified T : TLPrimitive<*>> write(value: T) {
        when (value) {
            is TLByte -> writeByte(value)
            is TLInt -> writeInt(value)
            is TLLong -> writeLong(value)
            is TLFloat -> writeFloat(value)
            is TLDouble -> writeDouble(value)
            is TLBoolean -> writeBoolean(value)
            is TLString -> writeString(value)
            is TLByteArray -> writeByteArray(value)
            else -> unsupportedValue(value)
        }
    }

    private fun writeHeader(dataSize: Int): Int {
        val beforeReadPosition = getPosition()
        if (dataSize < LONG_LENGTH_PREFIX) {
            writeIntAsByte(dataSize.toTLInt())
        } else {
            writeIntAsByte(LONG_LENGTH_PREFIX.toTLInt())
            val alreadyRead = getPosition() - beforeReadPosition
            val remainingHeaderBytes = PADDING_ALIGNMENT - alreadyRead
            encodeBytes(remainingHeaderBytes, dataSize.toLong(), BYTE_ORDER) { byte ->
                writeIntAsByte(byte.toTLInt())
            }
        }
        val afterReadPosition = getPosition()
        return afterReadPosition - beforeReadPosition
    }

    inline fun <T> readHeader(callback: (headerLength: Int, dataLength: Int) -> T): T {
        val beforeReadPosition = getPosition()
        var dataLength = readByteAsInt().toRaw()
        if (dataLength >= LONG_LENGTH_PREFIX) {
            val alreadyRead = getPosition() - beforeReadPosition
            val remainingHeaderBytes = PADDING_ALIGNMENT - alreadyRead
            dataLength = decodeBytes(remainingHeaderBytes, BYTE_ORDER) {
                readByteAsInt().toRaw()
            }.toInt()
        }
        val afterReadPosition = getPosition()
        val headerLength = afterReadPosition - beforeReadPosition
        return callback(headerLength, dataLength)
    }

    private fun writePadding(totalLength: Int) {
        repeat(totalLength.alignPadding(PADDING_ALIGNMENT)) {
            writeIntAsByte(TLInt.ZERO)
        }
    }

    fun skipPadding(headerLength: Int, dataLength: Int) {
        skip(
            (headerLength + dataLength).alignPadding(PADDING_ALIGNMENT)
        )
    }

    companion object {
        val BYTE_ORDER: ByteOrder = ByteOrder.LITTLE_ENDIAN

        const val LONG_LENGTH_PREFIX: Int = 254
        const val PADDING_ALIGNMENT: Int = 4
    }
}
