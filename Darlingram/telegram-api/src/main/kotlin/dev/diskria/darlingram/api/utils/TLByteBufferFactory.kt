package dev.diskria.darlingram.api.utils

import dev.diskria.darlingram.api.utils.TLByteBuffer.Companion.native_getJavaByteBuffer
import dev.diskria.darlingram.api.utils.TLByteBuffer.Companion.native_limit
import dev.diskria.darlingram.api.utils.TLByteBuffer.Companion.native_position
import java.util.LinkedList

object TLByteBufferFactory {

    private val buffers: ThreadLocal<LinkedList<TLByteBuffer>> =
        ThreadLocal.withInitial { LinkedList() }

    fun newInstance(address: Long): TLByteBuffer {
        require(address > 0) { error("Invalid address") }

        val buffer = native_getJavaByteBuffer(address).apply {
            val limit = native_limit(address)
            limit(limit)

            val position = native_position(address)
            if (position <= limit) {
                position(position)
            }
            order(TLProtocol.BYTE_ORDER)
        }
        val instance = buffers.get()?.poll()?.setBuffer(buffer) ?: TLByteBuffer(buffer, address)
        instance.setReused(false)
        return instance
    }

    fun recycle(buffer: TLByteBuffer) {
        buffers.get()?.add(buffer)
    }
}
