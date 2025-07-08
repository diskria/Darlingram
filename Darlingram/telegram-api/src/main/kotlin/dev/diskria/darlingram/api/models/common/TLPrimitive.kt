package dev.diskria.darlingram.api.models.common

interface TLPrimitive<T> : TLObject {
    fun toRaw(): T
}
