package dev.diskria.darlingram.api.models.common

import dev.diskria.darlingram.api.utils.TLProtocol

interface TLObject {

    fun getConstructor(): TLConstructor

    fun serializeInternal(output: TLProtocol) {

    }

    fun serialize(output: TLProtocol) {
        output.write(getConstructor())
        serializeInternal(output)
    }
}
