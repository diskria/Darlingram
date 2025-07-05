package dev.diskria.darlingram.toolkit.extensions

import com.android.build.api.dsl.VariantDimension
import dev.diskria.darlingram.tools.kotlin.extensions.wrapWithDoubleQuote

inline fun <reified T> VariantDimension.buildConfig(name: String, value: T) {
    val className = T::class.simpleName ?: error("Unknown")
    val isString = T::class.java == String::class.java
    val type = if (isString) className else className.lowercase()
    val stringified = value.toString()
    buildConfigField(
        type,
        name,
        if (isString) stringified.wrapWithDoubleQuote() else stringified
    )
}
