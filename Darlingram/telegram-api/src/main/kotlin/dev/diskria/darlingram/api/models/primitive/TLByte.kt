package dev.diskria.darlingram.api.models.primitive

import dev.diskria.darlingram.api.models.common.TLConstructor
import dev.diskria.darlingram.api.models.common.TLPrimitive

class TLByte(private val value: Byte) : TLPrimitive<Byte>() {

    override fun getConstructor(): TLConstructor = error("Unsupported type")

    override fun toRaw(): Byte = value
}
