package dev.diskria.darlingram.api.models.primitive

import dev.diskria.darlingram.api.models.common.TLConstructor
import dev.diskria.darlingram.api.models.common.TLPrimitive
import dev.diskria.darlingram.tools.kotlin.extensions.unsupportedOperation

@JvmInline
value class TLByte(private val value: Byte) : TLPrimitive<Byte> {

    override fun getConstructor(): TLConstructor = unsupportedOperation()

    override fun toRaw(): Byte = value
}
