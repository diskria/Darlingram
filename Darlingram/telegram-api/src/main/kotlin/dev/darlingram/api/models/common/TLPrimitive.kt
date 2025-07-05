package dev.darlingram.api.models.common

interface TLPrimitive<RAW_TYPE> {
    fun getRawValue(): RAW_TYPE
}
