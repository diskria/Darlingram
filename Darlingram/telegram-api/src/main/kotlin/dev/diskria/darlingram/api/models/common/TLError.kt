package dev.diskria.darlingram.api.models.common

import dev.diskria.darlingram.api.models.extensions.toTLConstructor
import dev.diskria.darlingram.api.models.primitive.TLInt
import dev.diskria.darlingram.api.models.primitive.TLString
import dev.diskria.darlingram.api.utils.TLProtocol

class TLError(
    val code: TLInt,
    val text: TLString,
) : TLObject {

    override fun getConstructor(): TLConstructor = CONSTRUCTOR

    override fun serializeInternal(output: TLProtocol) {
        output.write(code)
        output.write(text)
    }

    companion object {
        val CONSTRUCTOR: TLConstructor = 0xc4b9f9bb.toTLConstructor()

        fun deserialize(input: TLProtocol, constructor: TLConstructor): TLError? =
            if (constructor == CONSTRUCTOR) deserialize(input)
            else null

        fun deserialize(input: TLProtocol): TLError =
            TLError(
                input.read(),
                input.read()
            )
    }
}
