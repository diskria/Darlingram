package dev.diskria.darlingram.api.models.primitive

import dev.diskria.darlingram.api.models.common.TLConstructor
import dev.diskria.darlingram.api.models.common.TLPrimitive
import dev.diskria.darlingram.api.models.extensions.toTLDouble
import dev.diskria.darlingram.toolkit.utils.kotlin.extensions.unsupportedOperation

@JvmInline
value class TLDouble(private val value: Double) : TLPrimitive<Double> {

    override fun getConstructor(): TLConstructor = unsupportedOperation()

    override fun toRaw(): Double = value

    companion object {
        val ZERO: TLDouble = 0.toDouble().toTLDouble()
    }
}
