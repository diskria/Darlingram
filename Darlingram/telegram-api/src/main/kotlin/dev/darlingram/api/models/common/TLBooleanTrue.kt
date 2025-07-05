package dev.darlingram.api.models.common

class TLBooleanTrue : TLBoolean(true) {

    override fun getConstructor(): Int = CONSTRUCTOR

    companion object {
        const val CONSTRUCTOR: Int = 0x997275b5.toInt()
    }
}
