package dev.diskria.darlingram.api.models.primitive

import dev.diskria.darlingram.api.models.common.TLConstructor
import dev.diskria.darlingram.api.models.extensions.toTLConstructor

object TLBooleanTrue : TLBoolean() {

    val CONSTRUCTOR: TLConstructor = 0x997275b5.toTLConstructor()

    override fun getConstructor(): TLConstructor = CONSTRUCTOR

    override fun toRaw(): Boolean = true
}
