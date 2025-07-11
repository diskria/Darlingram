package dev.diskria.darlingram.api.utils

import dev.diskria.darlingram.api.models.extensions.decodeIntBytes
import dev.diskria.darlingram.api.models.extensions.decodeLongBytes
import dev.diskria.darlingram.api.models.extensions.toTLByte
import dev.diskria.darlingram.api.models.extensions.toTLInt
import dev.diskria.darlingram.api.models.extensions.toTLLong
import dev.diskria.darlingram.api.models.primitive.TLByte
import dev.diskria.darlingram.api.models.primitive.TLByteArray
import dev.diskria.darlingram.api.models.primitive.TLInt
import dev.diskria.darlingram.api.models.primitive.TLLong
import dev.diskria.darlingram.toolkit.utils.kotlin.extensions.tryCatch
import dev.diskria.darlingram.toolkit.utils.kotlin.extensions.unsupportedOperation
import java.io.ByteArrayInputStream
import java.io.DataInputStream
import java.io.File

class TLInputStream private constructor(inputStream: ByteArrayInputStream) : TLStream() {

    private val buffer: DataInputStream =
        DataInputStream(inputStream)

    override fun cleanup() {
        buffer.close()
    }

    override fun skip(length: Int) {
        buffer.skipBytes(length)
    }

    override fun readByte(defaultValue: TLByte?): TLByte =
        tryCatch(defaultValue) {
            buffer.readByte().toTLByte()
        } ?: error("Cannot read Byte")

    override fun readInt(defaultValue: TLInt?): TLInt =
        tryCatch(defaultValue) {
            decodeIntBytes(BYTE_ORDER, buffer::read).toTLInt()
        } ?: error("Cannot read Int")

    override fun readLong(defaultValue: TLLong?): TLLong =
        tryCatch(defaultValue) {
            decodeLongBytes(BYTE_ORDER, buffer::read).toTLLong()
        } ?: error("Cannot read Long")

    override fun readBytes(output: TLByteArray, offset: Int, length: Int) {
        buffer.read(output.toRaw(), offset, length)
    }

    override fun writeByte(value: TLByte) = unsupportedOperation()

    override fun writeInt(value: TLInt) = unsupportedOperation()

    override fun writeLong(value: TLLong) = unsupportedOperation()

    override fun writeBytes(value: TLByteArray, offset: Int, length: Int) = unsupportedOperation()

    companion object {
        fun newInstance(byteArray: ByteArray): TLInputStream =
            TLInputStream(byteArray.inputStream())

        fun newInstance(file: File): TLInputStream =
            newInstance(file.readBytes())
    }
}
