package dev.diskria.darlingram.api.models.primitive

import dev.diskria.darlingram.api.models.common.TLConstructor
import dev.diskria.darlingram.api.models.extensions.toTLConstructor

object TLBooleanFalse : TLBoolean() {

    val CONSTRUCTOR: TLConstructor = 0xbc799737.toTLConstructor()

    override fun getConstructor(): TLConstructor = CONSTRUCTOR

    override fun toRaw(): Boolean = false
}
