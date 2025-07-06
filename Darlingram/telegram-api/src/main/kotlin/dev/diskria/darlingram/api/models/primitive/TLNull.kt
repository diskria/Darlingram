package dev.diskria.darlingram.api.models.primitive

import dev.diskria.darlingram.api.models.common.TLConstructor
import dev.diskria.darlingram.api.models.common.TLPrimitive
import dev.diskria.darlingram.api.models.extensions.toTLConstructor

class TLNull : TLPrimitive<Void?>() {

    override fun getConstructor(): TLConstructor = CONSTRUCTOR

    override fun toRaw(): Void? = null

    companion object {
        val CONSTRUCTOR: TLConstructor = 0x56730bcc.toTLConstructor()

        val NULL: TLNull = TLNull()
    }
}
