package dev.diskria.darlingram.api.models.common

abstract class TLPrimitive<T> : TLObject() {
    abstract fun toRaw(): T
}
