package dev.diskria.darlingram.api.models.common

import dev.diskria.darlingram.api.models.extensions.toTLArray
import dev.diskria.darlingram.api.models.extensions.toTLConstructor
import dev.diskria.darlingram.api.models.extensions.toTLInt
import dev.diskria.darlingram.api.models.primitive.TLInt
import dev.diskria.darlingram.api.utils.TLProtocol
import dev.diskria.darlingram.toolkit.utils.kotlin.extensions.tryCatch

@JvmInline
value class TLArray<T : TLObject>(val list: List<T>) : TLObject {

    val size: Int
        get() = list.size

    override fun getConstructor(): TLConstructor = CONSTRUCTOR

    override fun serializeInternal(output: TLProtocol) {
        serializeWithoutConstructor(output, list)
    }

    companion object {
        val CONSTRUCTOR: TLConstructor = 0x1cb5c415.toTLConstructor()

        inline fun <reified T : TLObject> deserialize(
            input: TLProtocol,
            constructor: TLConstructor = input.read(),
            deserializer: (input: TLProtocol, constructor: TLInt) -> T,
            defaultValue: TLArray<T>? = null,
        ): TLArray<T> =
            tryCatch(defaultValue) {
                takeIf { constructor == CONSTRUCTOR }?.run {
                    val size: TLInt = input.read()
                    List(size.toRaw()) {
                        deserializer(input, input.read())
                    }.toTLArray()
                }
            } ?: error("Cannot read TLArray")

        fun <T : TLObject> serialize(output: TLProtocol, list: List<T>) {
            output.write(CONSTRUCTOR)
            serializeWithoutConstructor(output, list)
        }

        private fun <T : TLObject> serializeWithoutConstructor(
            output: TLProtocol,
            list: List<T>,
        ) {
            output.write(list.size.toTLInt())
            list.forEach { it.serialize(output) }
        }
    }
}
