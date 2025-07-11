package dev.diskria.darlingram.api.utils

import dev.diskria.darlingram.api.utils.TLByteBuffer.Companion.native_getJavaByteBuffer
import dev.diskria.darlingram.api.utils.TLByteBuffer.Companion.native_limit
import dev.diskria.darlingram.api.utils.TLByteBuffer.Companion.native_position
import dev.diskria.darlingram.toolkit.utils.kotlin.extensions.invalidValue
import java.util.LinkedList

object TLByteBufferFactory {

    private val buffers: ThreadLocal<LinkedList<TLByteBuffer>> =
        ThreadLocal.withInitial { LinkedList() }

    fun newInstance(nativeBufferPointer: Long): TLByteBuffer {
        require(nativeBufferPointer > 0) { invalidValue(nativeBufferPointer) }

        val buffer = native_getJavaByteBuffer(nativeBufferPointer).apply {
            val limit = native_limit(nativeBufferPointer)
            limit(limit)

            val position = native_position(nativeBufferPointer)
            if (position <= limit) {
                position(position)
            }
            order(TLProtocol.BYTE_ORDER)
        }
        val instance = buffers.get()?.poll()?.setBuffer(buffer) ?: TLByteBuffer(buffer, nativeBufferPointer)
        instance.setReused(false)
        return instance
    }

    fun recycle(buffer: TLByteBuffer) {
        buffers.get()?.add(buffer)
    }
}
