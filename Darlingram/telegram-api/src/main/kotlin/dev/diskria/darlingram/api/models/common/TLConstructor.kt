package dev.diskria.darlingram.api.models.common

@JvmInline
value class TLConstructor(private val id: Int) : TLPrimitive<Int> {

    override fun getConstructor(): TLConstructor = this

    override fun toRaw(): Int = id
}
