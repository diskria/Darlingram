package dev.diskria.darlingram.api.models.primitive

import dev.diskria.darlingram.api.models.common.TLConstructor
import dev.diskria.darlingram.api.models.common.TLPrimitive
import dev.diskria.darlingram.api.models.extensions.toTLFloat
import dev.diskria.darlingram.tools.kotlin.extensions.unsupportedOperation

@JvmInline
value class TLFloat(private val value: Float) : TLPrimitive<Float> {

    override fun getConstructor(): TLConstructor = unsupportedOperation()

    override fun toRaw(): Float = value

    companion object {
        val ZERO: TLFloat = 0.toFloat().toTLFloat()
    }
}
