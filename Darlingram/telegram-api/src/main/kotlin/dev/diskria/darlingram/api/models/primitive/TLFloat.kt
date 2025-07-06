package dev.diskria.darlingram.api.models.primitive

import dev.diskria.darlingram.api.models.common.TLConstructor
import dev.diskria.darlingram.api.models.common.TLPrimitive
import dev.diskria.darlingram.api.models.extensions.toTLFloat

class TLFloat(private val value: Float) : TLPrimitive<Float>() {

    override fun getConstructor(): TLConstructor = error("Unsupported type")

    override fun toRaw(): Float = value

    companion object {
        val ZERO: TLFloat = 0.toFloat().toTLFloat()
    }
}
