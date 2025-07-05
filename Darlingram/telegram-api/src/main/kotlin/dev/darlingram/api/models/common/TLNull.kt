package dev.darlingram.api.models.common

class TLNull : TLObject(), TLPrimitive<Void?> {

    override fun getRawValue(): Void? = null

    override fun getConstructor(): Int = 0x56730bcc
}
