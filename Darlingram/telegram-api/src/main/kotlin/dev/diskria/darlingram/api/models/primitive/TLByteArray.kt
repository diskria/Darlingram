package dev.diskria.darlingram.api.models.primitive

import dev.diskria.darlingram.api.models.common.TLConstructor
import dev.diskria.darlingram.api.models.common.TLPrimitive
import dev.diskria.darlingram.tools.kotlin.extensions.unsupportedOperation

class TLByteArray(private val value: ByteArray) : TLPrimitive<ByteArray>() {

    val size: Int
        get() = value.size

    override fun getConstructor(): TLConstructor = unsupportedOperation()

    override fun toRaw(): ByteArray = value
}
