package dev.diskria.darlingram.api.models.primitive

import dev.diskria.darlingram.api.models.common.TLConstructor
import dev.diskria.darlingram.api.models.common.TLPrimitive
import dev.diskria.darlingram.api.models.extensions.toTLString
import dev.diskria.darlingram.tools.kotlin.utils.Constants

class TLString(private val value: String) : TLPrimitive<String>() {

    override fun getConstructor(): TLConstructor = error("Unsupported type")

    override fun toRaw(): String = value

    companion object {
        val EMPTY: TLString = Constants.Symbol.EMPTY.toTLString()
    }
}
