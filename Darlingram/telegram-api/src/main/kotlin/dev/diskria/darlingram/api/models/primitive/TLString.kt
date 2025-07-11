package dev.diskria.darlingram.api.models.primitive

import dev.diskria.darlingram.api.models.common.TLConstructor
import dev.diskria.darlingram.api.models.common.TLPrimitive
import dev.diskria.darlingram.api.models.extensions.toTLString
import dev.diskria.darlingram.toolkit.utils.kotlin.extensions.unsupportedOperation
import dev.diskria.darlingram.toolkit.utils.kotlin.utils.Constants

@JvmInline
value class TLString(private val value: String) : TLPrimitive<String> {

    override fun getConstructor(): TLConstructor = unsupportedOperation()

    override fun toRaw(): String = value

    companion object {
        val EMPTY: TLString = Constants.Symbol.EMPTY.toTLString()
    }
}
