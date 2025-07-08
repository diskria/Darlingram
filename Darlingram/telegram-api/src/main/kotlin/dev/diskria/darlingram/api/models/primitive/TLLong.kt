package dev.diskria.darlingram.api.models.primitive

import dev.diskria.darlingram.api.models.common.TLConstructor
import dev.diskria.darlingram.api.models.common.TLPrimitive
import dev.diskria.darlingram.api.models.extensions.mapToInt
import dev.diskria.darlingram.api.models.extensions.toTLConstructor
import dev.diskria.darlingram.api.models.extensions.toTLLong
import dev.diskria.darlingram.api.utils.TLProtocol
import dev.diskria.darlingram.tools.kotlin.extensions.packIntsToLong
import dev.diskria.darlingram.tools.kotlin.extensions.unpackHighInt

@JvmInline
value class TLLong(private val value: Long) : TLPrimitive<Long> {

    override fun getConstructor(): TLConstructor = value.unpackHighInt().toTLConstructor()

    override fun serialize(output: TLProtocol) {
        output.write(value.toTLLong())
    }

    override fun toRaw(): Long = value

    companion object {
        val ZERO: TLLong = 0.toLong().toTLLong()

        fun deserialize(
            input: TLProtocol,
            constructor: TLInt,
            defaultValue: TLLong,
        ): TLLong =
            packIntsToLong(
                input.read(defaultValue).mapToInt(),
                constructor.toRaw()
            ).toTLLong()
    }
}
