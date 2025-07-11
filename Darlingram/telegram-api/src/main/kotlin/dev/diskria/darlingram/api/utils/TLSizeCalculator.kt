package dev.diskria.darlingram.api.utils

import dev.diskria.darlingram.api.models.primitive.TLByte
import dev.diskria.darlingram.api.models.primitive.TLByteArray
import dev.diskria.darlingram.api.models.primitive.TLInt
import dev.diskria.darlingram.api.models.primitive.TLLong
import dev.diskria.darlingram.toolkit.utils.kotlin.extensions.unsupportedOperation
import java.nio.ByteBuffer

class TLSizeCalculator : TLProtocol() {

    private var size: Int = 0

    fun reset() {
        size = 0
    }

    override fun getPosition(): Int =
        size

    override fun skip(length: Int) {
        size += length
    }

    override fun writeByte(value: TLByte) {
        size += Byte.SIZE_BYTES
    }

    override fun writeInt(value: TLInt) {
        size += Int.SIZE_BYTES
    }

    override fun writeLong(value: TLLong) {
        size += Long.SIZE_BYTES
    }

    override fun writeBytes(value: TLByteArray, offset: Int, length: Int) {
        size += length
    }

    override fun writeBytes(byteBuffer: ByteBuffer) {
        size += byteBuffer.limit()
    }

    override fun cleanup() = unsupportedOperation()

    override fun readByte(defaultValue: TLByte?): TLByte = unsupportedOperation()

    override fun readInt(defaultValue: TLInt?): TLInt = unsupportedOperation()

    override fun readLong(defaultValue: TLLong?): TLLong = unsupportedOperation()

    override fun readBytes(output: TLByteArray, offset: Int, length: Int) = unsupportedOperation()

    override fun readBytes(dataSize: Int): ByteBuffer = unsupportedOperation()
}
