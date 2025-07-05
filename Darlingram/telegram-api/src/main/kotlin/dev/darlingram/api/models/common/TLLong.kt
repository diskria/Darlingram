package dev.darlingram.api.models.common

import dev.diskria.darlingram.tools.kotlin.extensions.packIntsToLong
import dev.diskria.darlingram.tools.kotlin.extensions.unpackHighInt
import dev.diskria.darlingram.tools.kotlin.extensions.unpackLowInt
import dev.darlingram.api.utils.SerializedInput
import dev.darlingram.api.utils.SerializedOutput
import dev.darlingram.api.utils.read
import dev.darlingram.api.utils.write

class TLLong(val rawValue: Long) : TLObject(), TLPrimitive<Long> {

    override fun getRawValue(): Long = rawValue

    override fun getConstructor(): Int = rawValue.unpackHighInt()

    override fun serializeInternal(output: SerializedOutput) {
        output.write(rawValue.unpackLowInt())
    }

    companion object {
        fun deserialize(
            input: SerializedInput,
            throwOnError: Boolean,
            constructor: Int,
        ): TLLong =
            TLLong(packIntsToLong(constructor, input.read(throwOnError)))
    }
}
