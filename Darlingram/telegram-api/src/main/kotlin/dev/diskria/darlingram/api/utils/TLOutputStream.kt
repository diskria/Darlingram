package dev.diskria.darlingram.api.utils

import dev.diskria.darlingram.api.models.extensions.encodeIntBytes
import dev.diskria.darlingram.api.models.extensions.encodeLongBytes
import dev.diskria.darlingram.api.models.primitive.TLByte
import dev.diskria.darlingram.api.models.primitive.TLByteArray
import dev.diskria.darlingram.api.models.primitive.TLInt
import dev.diskria.darlingram.api.models.primitive.TLLong
import dev.diskria.darlingram.tools.kotlin.extensions.unsupportedOperation
import java.io.ByteArrayOutputStream
import java.io.DataOutputStream

class TLOutputStream(length: Int? = null) : TLProtocol() {

    private val stream: ByteArrayOutputStream =
        length?.let { ByteArrayOutputStream(it) } ?: ByteArrayOutputStream()

    private val buffer: DataOutputStream =
        DataOutputStream(stream)

    override fun getLength(): Int = stream.size()

    override fun getPosition(): Int = 0

    override fun remaining(): Int = Int.MAX_VALUE

    override fun cleanup() {
        stream.close()
        buffer.close()
    }

    override fun toByteArray(): ByteArray = stream.toByteArray()

    override fun writeByte(value: TLByte) {
        buffer.writeByte(value.toRaw().toInt())
    }

    override fun writeInt(value: TLInt) {
        encodeIntBytes(value.toRaw(), BYTE_ORDER, buffer::write)
    }

    override fun writeLong(value: TLLong) {
        encodeLongBytes(value.toRaw(), BYTE_ORDER, buffer::write)
    }

    override fun writeBytes(value: TLByteArray, offset: Int, length: Int) {
        buffer.write(value.toRaw(), offset, length)
    }

    override fun skip(length: Int) = unsupportedOperation()

    override fun readByte(defaultValue: TLByte?): TLByte = unsupportedOperation()

    override fun readInt(defaultValue: TLInt?): TLInt = unsupportedOperation()

    override fun readLong(defaultValue: TLLong?): TLLong = unsupportedOperation()

    override fun readBytes(output: TLByteArray, offset: Int, length: Int) = unsupportedOperation()
}
