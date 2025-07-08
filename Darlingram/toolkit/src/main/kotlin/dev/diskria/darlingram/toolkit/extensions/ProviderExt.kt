package dev.diskria.darlingram.toolkit.extensions

import dev.diskria.darlingram.tools.kotlin.extensions.unsupportedType
import org.gradle.api.provider.Provider

inline fun <reified T> Provider<String>.value(): T {
    val rawString = get()
    return when (T::class) {
        Boolean::class -> rawString.toBoolean() as T
        Int::class -> rawString.toInt() as T
        Long::class -> rawString.toLong() as T
        Float::class -> rawString.toFloat() as T
        Double::class -> rawString.toDouble() as T
        String::class -> rawString as T
        else -> unsupportedType(T::class)
    }
}
