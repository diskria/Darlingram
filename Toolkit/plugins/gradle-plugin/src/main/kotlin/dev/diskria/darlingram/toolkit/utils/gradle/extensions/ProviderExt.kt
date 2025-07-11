package dev.diskria.darlingram.toolkit.utils.gradle.extensions

import dev.diskria.darlingram.tools.kotlin.extensions.unsupportedType
import org.gradle.api.provider.Provider

inline fun <reified T> Provider<String>.value(): T {
    val rawString = get()
    return when (T::class) {
        Boolean::class -> rawString.toBoolean()
        Int::class -> rawString.toInt()
        Long::class -> rawString.toLong()
        Float::class -> rawString.toFloat()
        Double::class -> rawString.toDouble()
        String::class -> rawString
        else -> unsupportedType(T::class)
    } as T
}
