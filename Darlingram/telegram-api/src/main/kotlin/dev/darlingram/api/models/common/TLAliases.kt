package dev.darlingram.api.models.common

import dev.darlingram.api.utils.SerializedInput

interface TLDeserializer<T : TLObject> {
    fun deserialize(input: SerializedInput, throwOnError: Boolean, constructor: Int): T?
}
