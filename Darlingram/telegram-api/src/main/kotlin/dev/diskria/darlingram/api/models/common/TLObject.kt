package dev.diskria.darlingram.api.models.common

import dev.diskria.darlingram.api.utils.TLProtocol

abstract class TLObject {

    abstract fun getConstructor(): TLConstructor

    protected open fun serializeInternal(output: TLProtocol) {

    }

    open fun serialize(output: TLProtocol) {
        output.write(getConstructor())
        serializeInternal(output)
    }
}
