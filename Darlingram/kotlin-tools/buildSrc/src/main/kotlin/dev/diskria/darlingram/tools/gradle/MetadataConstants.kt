package dev.diskria.darlingram.tools.gradle

import java.util.Locale
import kotlin.reflect.KProperty

class MetadataConstants(private val properties: Map<String, Any?>) {

    operator fun getValue(thisRef: Any?, property: KProperty<*>): Pair<String, String> {
        val key = property.name
        val value = properties[key] as? String ?: error("Key '$key' not found or is not a String")
        return key.toConstantName() to value
    }

    private fun String.toConstantName(): String =
        this
            .replace(Regex("([a-z])([A-Z]+)"), "$1_$2")
            .uppercase(Locale.ROOT)
}

fun Map<String, Any?>.metadataConstants(): MetadataConstants =
    MetadataConstants(this)
