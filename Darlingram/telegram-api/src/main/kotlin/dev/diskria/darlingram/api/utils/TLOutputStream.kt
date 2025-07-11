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

class TLOutputStream private constructor(outputStream: ByteArrayOutputStream) : TLStream() {

    private val dataOutput: DataOutputStream =
        DataOutputStream(outputStream)

    override fun cleanup() {
        dataOutput.close()
    }

    override fun writeByte(value: TLByte) {
        dataOutput.writeByte(value.toRaw().toInt())
    }

    override fun writeInt(value: TLInt) {
        encodeIntBytes(value.toRaw(), BYTE_ORDER, dataOutput::write)
    }

    override fun writeLong(value: TLLong) {
        encodeLongBytes(value.toRaw(), BYTE_ORDER, dataOutput::write)
    }

    override fun writeBytes(value: TLByteArray, offset: Int, length: Int) {
        dataOutput.write(value.toRaw(), offset, length)
    }

    override fun skip(length: Int) = unsupportedOperation()

    override fun readByte(defaultValue: TLByte?): TLByte = unsupportedOperation()

    override fun readInt(defaultValue: TLInt?): TLInt = unsupportedOperation()

    override fun readLong(defaultValue: TLLong?): TLLong = unsupportedOperation()

    override fun readBytes(output: TLByteArray, offset: Int, length: Int) = unsupportedOperation()

    companion object {
        fun newInstance(length: Int? = null): TLOutputStream =
            TLOutputStream(
                if (length != null) ByteArrayOutputStream(length)
                else ByteArrayOutputStream()
            )
    }
}
