package dev.darlingram.api.models.common

import dev.diskria.darlingram.tools.kotlin.extensions.className

abstract class TLBoolean(private val rawValue: Boolean) : TLObject(), TLPrimitive<Boolean> {

    override fun getRawValue(): Boolean = rawValue

    companion object {
        fun deserialize(
            throwOnError: Boolean,
            constructor: Int,
        ): TLBoolean? =
            when (constructor) {
                TLBooleanTrue.CONSTRUCTOR -> TLBooleanTrue()
                TLBooleanFalse.CONSTRUCTOR -> TLBooleanFalse()
                else -> when {
                    throwOnError -> error("Can't parse constructor $constructor in ${className()}")
                    else -> null
                }
            }
    }
}
