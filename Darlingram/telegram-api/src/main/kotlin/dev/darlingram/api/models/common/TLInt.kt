package dev.darlingram.api.models.common

class TLInt(private val rawValue: Int) : TLObject(), TLPrimitive<Int> {

    override fun getRawValue(): Int = rawValue

    override fun getConstructor(): Int = rawValue

    companion object {
        fun deserialize(constructor: Int): TLInt =
            TLInt(constructor)
    }
}
