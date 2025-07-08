package dev.diskria.darlingram.api.models.primitive

import dev.diskria.darlingram.api.models.common.TLConstructor
import dev.diskria.darlingram.api.models.common.TLPrimitive

sealed class TLBoolean : TLPrimitive<Boolean>() {

    abstract override fun getConstructor(): TLConstructor

    abstract override fun toRaw(): Boolean

    companion object {
        fun fromRaw(value: Boolean): TLBoolean =
            if (value) TLBooleanTrue
            else TLBooleanFalse

        fun deserialize(constructor: TLConstructor, defaultValue: TLBoolean? = null): TLBoolean =
            when (constructor) {
                TLBooleanTrue.CONSTRUCTOR -> TLBooleanTrue
                TLBooleanFalse.CONSTRUCTOR -> TLBooleanFalse
                else -> defaultValue ?: error("Cannot read Boolean")
            }
    }
}
