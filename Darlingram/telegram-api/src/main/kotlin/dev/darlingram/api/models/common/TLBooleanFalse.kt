package dev.darlingram.api.models.common

class TLBooleanFalse : TLBoolean(false) {

    override fun getConstructor(): Int = CONSTRUCTOR

    companion object {
        const val CONSTRUCTOR: Int = 0xbc799737.toInt()
    }
}
