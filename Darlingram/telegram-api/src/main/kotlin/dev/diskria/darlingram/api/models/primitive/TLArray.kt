package dev.diskria.darlingram.api.models.primitive

import dev.diskria.darlingram.api.models.common.TLConstructor
import dev.diskria.darlingram.api.models.common.TLObject
import dev.diskria.darlingram.api.models.extensions.toTLConstructor
import dev.diskria.darlingram.api.models.extensions.toTLArray
import dev.diskria.darlingram.api.models.extensions.toTLInt
import dev.diskria.darlingram.api.utils.TLProtocol
import dev.diskria.darlingram.tools.kotlin.extensions.tryCatch

class TLArray<T : TLObject>(val list: List<T>) : TLObject() {

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
            } ?: error("Can't parse TLVector")

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
