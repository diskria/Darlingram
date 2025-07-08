package dev.diskria.darlingram.api.models.primitive

import dev.diskria.darlingram.api.models.common.TLConstructor
import dev.diskria.darlingram.api.models.common.TLPrimitive
import dev.diskria.darlingram.api.models.extensions.toTLConstructor
import dev.diskria.darlingram.api.utils.TLProtocol

@JvmInline
value class TLInt(val value: Int) : TLPrimitive<Int> {

    override fun getConstructor(): TLConstructor = value.toTLConstructor()

    override fun toRaw(): Int = value

    companion object {
        val ZERO: TLInt = TLInt(0)

        fun deserialize(input: TLProtocol, constructor: Int, defaultValue: TLInt): TLInt =
            input.read(defaultValue)
    }
}
