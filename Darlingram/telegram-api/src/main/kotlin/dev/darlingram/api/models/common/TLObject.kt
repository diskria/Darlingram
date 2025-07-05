package dev.darlingram.api.models.common

import dev.darlingram.api.utils.SerializedOutput
import dev.darlingram.api.utils.write

abstract class TLObject {

    abstract fun getConstructor(): Int

    protected open fun serializeInternal(output: SerializedOutput) {

    }

    fun serialize(output: SerializedOutput) {
        output.write(getConstructor())
        serializeInternal(output)
    }
}
