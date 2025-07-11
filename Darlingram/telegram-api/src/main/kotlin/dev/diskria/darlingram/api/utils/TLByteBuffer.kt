package dev.diskria.darlingram.api.utils

import dev.diskria.darlingram.api.models.extensions.toTLByte
import dev.diskria.darlingram.api.models.extensions.toTLInt
import dev.diskria.darlingram.api.models.extensions.toTLLong
import dev.diskria.darlingram.api.models.primitive.TLByte
import dev.diskria.darlingram.api.models.primitive.TLByteArray
import dev.diskria.darlingram.api.models.primitive.TLInt
import dev.diskria.darlingram.api.models.primitive.TLLong
import dev.diskria.darlingram.toolkit.utils.kotlin.extensions.invalidValue
import dev.diskria.darlingram.toolkit.utils.kotlin.extensions.tryCatch
import java.nio.ByteBuffer

class TLByteBuffer(
    private var buffer: ByteBuffer,
    private val nativeBufferPointer: Long,
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

    override fun getPosition(): Int =
        buffer.position()

    override fun cleanup() {
        buffer.clear()
    }

    override fun skip(length: Int) {
        setPosition(getPosition() + length)
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

    override fun writeBytes(value: TLByteArray, offset: Int, length: Int) {
        buffer.put(value.toRaw(), offset, length)
    }

    override fun readBytes(output: TLByteArray, offset: Int, length: Int) {
        buffer.get(output.toRaw(), offset, length)
    }

    override fun writeBytes(byteBuffer: ByteBuffer) {
        byteBuffer.rewind()
        buffer.put(byteBuffer)
    }

    override fun readBytes(dataSize: Int): ByteBuffer =
        newInstance(dataSize).let { newBuffer ->
            val oldLimit = getLimit()
            setLimit(getPosition() + dataSize)
            newBuffer.writeBytes(buffer)
            setLimit(oldLimit)
            newBuffer.setPosition(0)
            return newBuffer.buffer
        }

    override fun close() {
        if (isReused) {
            return
        }
        isReused = true
        TLByteBufferFactory.recycle(this)
        native_reuse(nativeBufferPointer)
    }

    private fun setPosition(position: Int) {
        buffer.position(position)
    }

    private fun setLimit(limit: Int) {
        buffer.limit(limit)
    }

    private fun getLimit(): Int =
        buffer.limit()

    companion object {
        fun newInstance(dataSize: Int): TLByteBuffer {
            require(dataSize > 0) { invalidValue(dataSize) }
            val nativePointer = native_getFreeBuffer(dataSize)
            require(nativePointer > 0) { invalidValue(nativePointer) }
            return TLByteBuffer(
                native_getJavaByteBuffer(nativePointer).order(BYTE_ORDER),
                nativePointer
            ).apply {
                setPosition(0)
                setLimit(dataSize)
            }
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
