package dev.diskria.darlingram.api.utils

import dev.diskria.darlingram.api.models.primitive.TLByte
import dev.diskria.darlingram.api.models.primitive.TLByteArray
import dev.diskria.darlingram.api.models.primitive.TLInt
import dev.diskria.darlingram.api.models.primitive.TLLong
import dev.diskria.darlingram.tools.kotlin.extensions.unsupportedOperation

class TLSizeCalculator : TLProtocol() {

    private var count: Int = 0

    override fun getLength(): Int = count

    override fun getPosition(): Int = count

    override fun skip(length: Int) {
        count += length
    }

    override fun writeByte(value: TLByte) {
        count += Byte.SIZE_BYTES
    }

    override fun writeInt(value: TLInt) {
        count += Int.SIZE_BYTES
    }

    override fun writeLong(value: TLLong) {
        count += Long.SIZE_BYTES
    }

    override fun writeBytes(value: TLByteArray, offset: Int, length: Int) {
        count += length
    }

    override fun remaining(): Int = unsupportedOperation()

    override fun cleanup() = unsupportedOperation()

    override fun toByteArray(): ByteArray = unsupportedOperation()

    override fun readByte(defaultValue: TLByte?): TLByte = unsupportedOperation()

    override fun readInt(defaultValue: TLInt?): TLInt = unsupportedOperation()

    override fun readLong(defaultValue: TLLong?): TLLong = unsupportedOperation()

    override fun readBytes(output: TLByteArray, offset: Int, length: Int) = unsupportedOperation()
}
