package dev.diskria.darlingram.api.utils

import dev.diskria.darlingram.api.models.extensions.toTLByte
import dev.diskria.darlingram.api.models.extensions.toTLInt
import dev.diskria.darlingram.api.models.extensions.toTLLong
import dev.diskria.darlingram.api.models.primitive.TLByte
import dev.diskria.darlingram.api.models.primitive.TLByteArray
import dev.diskria.darlingram.api.models.primitive.TLInt
import dev.diskria.darlingram.api.models.primitive.TLLong
import dev.diskria.darlingram.tools.kotlin.extensions.tryCatch
import java.nio.ByteBuffer

class TLByteBuffer(
    private var buffer: ByteBuffer,
    private val address: Long,
) : TLProtocol(), AutoCloseable {

    private var isReused: Boolean = true

    fun setBuffer(buffer: ByteBuffer): TLByteBuffer {
        this.buffer = buffer
        return this
    }

    fun setReused(isReused: Boolean): TLByteBuffer {
        this.isReused = isReused
        return this
    }

    override fun getLength(): Int = getPosition()

    override fun getPosition(): Int = buffer.position()

    override fun remaining(): Int = buffer.remaining()

    override fun cleanup() {
        buffer.clear()
    }

    override fun toByteArray(): ByteArray = buffer.array()

    override fun skip(length: Int) {
        buffer.position(buffer.position() + length)
    }

    override fun writeByte(value: TLByte) {
        buffer.put(value.toRaw())
    }

    override fun readByte(defaultValue: TLByte?): TLByte =
        tryCatch(defaultValue) {
            buffer.get().toTLByte()
        } ?: error("Cannot read Byte")

    override fun writeInt(value: TLInt) {
        buffer.putInt(value.toRaw())
    }

    override fun readInt(defaultValue: TLInt?): TLInt =
        tryCatch(defaultValue) {
            buffer.getInt().toTLInt()
        } ?: error("Cannot read Int")

    override fun writeLong(value: TLLong) {
        buffer.putLong(value.toRaw())
    }

    override fun readLong(defaultValue: TLLong?): TLLong =
        tryCatch(defaultValue) {
            buffer.getLong().toTLLong()
        } ?: error("Cannot read Long")

    override fun writeBytes(
        value: TLByteArray,
        offset: Int,
        length: Int,
    ) {
        buffer.put(value.toRaw(), offset, length)
    }

    override fun readBytes(
        output: TLByteArray,
        offset: Int,
        length: Int,
    ) {
        buffer.get(output.toRaw(), offset, length)
    }

    override fun close() {
        if (isReused) {
            return
        }
        isReused = true
        TLByteBufferFactory.recycle(this)
        native_reuse(address)
    }

    companion object {
        fun newInstance(size: Int): TLByteBuffer {
            require(size > 0) { error("Invalid TLByteBuffer size") }
            val address = native_getFreeBuffer(size)
            require(address > 0) { error("Invalid address") }
            return TLByteBuffer(
                native_getJavaByteBuffer(address).apply {
                    position(0)
                    limit(size)
                    order(BYTE_ORDER)
                },
                address
            )
        }

        @JvmStatic
        external fun native_getFreeBuffer(length: Int): Long

        @JvmStatic
        external fun native_getJavaByteBuffer(address: Long): ByteBuffer

        @JvmStatic
        external fun native_limit(address: Long): Int

        @JvmStatic
        external fun native_position(address: Long): Int

        @JvmStatic
        external fun native_reuse(address: Long)
    }
}
