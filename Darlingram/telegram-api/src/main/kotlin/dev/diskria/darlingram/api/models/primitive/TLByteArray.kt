package dev.diskria.darlingram.api.models.primitive

import dev.diskria.darlingram.api.models.common.TLConstructor
import dev.diskria.darlingram.api.models.common.TLPrimitive

class TLByteArray(private val value: ByteArray) : TLPrimitive<ByteArray>() {

    val size: Int
        get() = value.size

    override fun getConstructor(): TLConstructor = error("Unsupported type")

    override fun toRaw(): ByteArray = value
}
