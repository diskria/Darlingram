package dev.diskria.darlingram.api.utils

import dev.diskria.darlingram.toolkit.utils.kotlin.extensions.unsupportedOperation
import java.nio.ByteBuffer

abstract class TLStream : TLProtocol() {

    override fun getPosition(): Int = unsupportedOperation()

    override fun writeBytes(byteBuffer: ByteBuffer) = unsupportedOperation()

    override fun readBytes(dataSize: Int): ByteBuffer = unsupportedOperation()
}
