package dev.darlingram.api.models.common

import dev.darlingram.api.utils.SerializedInput
import dev.darlingram.api.utils.SerializedOutput
import dev.darlingram.api.utils.read
import dev.darlingram.api.utils.write

class TLError(
    val code: Int,
    val text: String
) : TLObject() {

    override fun getConstructor(): Int = CONSTRUCTOR

    override fun serializeInternal(output: SerializedOutput) {
        output.write(code)
        output.write(text)
    }

    companion object {
        const val CONSTRUCTOR: Int = 0xc4b9f9bb.toInt()

        fun deserialize(
            input: SerializedInput,
            throwOnError: Boolean,
            constructor: Int,
        ): TLError? =
            if (constructor == CONSTRUCTOR) deserialize(input, throwOnError)
            else null

        fun deserialize(
            input: SerializedInput,
            throwOnError: Boolean
        ): TLError =
            TLError(
                input.read(throwOnError),
                input.read(throwOnError)
            )
    }
}
