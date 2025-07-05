package dev.darlingram.api.models.common

import dev.darlingram.api.utils.SerializedInput
import dev.darlingram.api.utils.SerializedOutput
import dev.darlingram.api.utils.read
import dev.darlingram.api.utils.write

class TLVector<T : TLObject>(val list: List<TLObject>) : TLObject() {

    override fun getConstructor(): Int = CONSTRUCTOR

    override fun serializeInternal(output: SerializedOutput) {
        serializeWithoutConstructor(output, list)
    }

    companion object {
        const val CONSTRUCTOR: Int = 0x1cb5c415

        inline fun <reified T : TLObject> deserialize(
            input: SerializedInput,
            throwOnError: Boolean,
            constructor: Int = input.read(throwOnError),
            elementDeserializer: TLDeserializer<T>
        ): TLVector<T>? =
            if (constructor == CONSTRUCTOR)
                TLVector(List(input.read(throwOnError)) {
                    elementDeserializer.deserialize(
                        input,
                        throwOnError,
                        input.read(throwOnError),
                    ) ?: TLNull()
                })
            else null

        fun <T : TLObject> serialize(output: SerializedOutput, list: List<T>) {
            output.write(CONSTRUCTOR)
            serializeWithoutConstructor(output, list)
        }

        private fun <T : TLObject> serializeWithoutConstructor(
            output: SerializedOutput,
            list: List<T>
        ) {
            output.write(list.size)
            list.forEach { it.serialize(output) }
        }
    }
}
