package dev.diskria.darlingram.api.models.primitive

import dev.diskria.darlingram.api.models.common.TLConstructor
import dev.diskria.darlingram.api.models.common.TLPrimitive
import dev.diskria.darlingram.api.models.extensions.toTLDouble

class TLDouble(private val value: Double) : TLPrimitive<Double>() {

    override fun getConstructor(): TLConstructor = error("Unsupported type")

    override fun toRaw(): Double = value

    companion object {
        val ZERO: TLDouble = 0.toDouble().toTLDouble()
    }
}
