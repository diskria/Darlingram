package dev.diskria.darlingram.toolkit.utils.kotlin.extensions

import dev.diskria.darlingram.toolkit.utils.kotlin.utils.BracketsType
import dev.diskria.darlingram.toolkit.utils.kotlin.utils.Constants
import java.io.File
import java.util.Locale

inline fun String?.ifNullOrEmpty(
    crossinline fallback: () -> String,
): String =
    if (isNullOrEmpty()) fallback()
    else this

fun fileName(name: String, vararg extensions: String): String =
    name + extensions.joinToString(separator = Constants.Symbol.EMPTY) { extension ->
        Constants.Symbol.DOT + extension
    }

fun String.capitalizeOnlyFirstChar(): String =
    lowercase(Locale.ROOT).replaceFirstChar { it.uppercaseChar() }

fun String.equalsIgnoreCase(other: String?): Boolean =
    equals(other, ignoreCase = true)

fun String.toChar(): Char? =
    singleOrNull()

fun String.toCharOrThrow(): Char =
    toChar() ?: error("Cannot convert string ${this.wrapWithDoubleQuote()} to a single character")

fun String.splitByComma(): List<String> = split(Constants.Symbol.COMMA)

fun String.invertCase(): String =
    map { char ->
        if (char.isLowerCase()) char.uppercaseChar()
        else char.lowercaseChar()
    }.joinToString(Constants.Symbol.EMPTY)

fun String.wrap(char: Char): String =
    char + this + char

fun String.wrap(bracketsType: BracketsType, count: Int = 1): String =
    bracketsType.openingSymbol.repeat(count) + this + bracketsType.closingSymbol.repeat(count)

fun String.wrapWithDoubleQuote(): String =
    wrap(Constants.Symbol.DOUBLE_QUOTE.toCharOrThrow())

fun String.toPackageName(): String =
    replace(Regex("[^\\p{L}]"), Constants.Symbol.DOT)

fun String.appendPackageName(packageName: String): String =
    this + if (packageName.startsWith(Constants.Symbol.DOT)) {
        packageName
    } else {
        Constants.Symbol.DOT + packageName
    }

fun String.toFile(): File =
    File(this)

inline fun <reified T> String.toTypedOrNull(): T? =
    when (T::class) {
        Boolean::class -> toBooleanStrictOrNull()
        Int::class -> toIntOrNull()
        Long::class -> toLongOrNull()
        Float::class -> toFloatOrNull()
        Double::class -> toDoubleOrNull()
        String::class -> this
        else -> unsupportedType(T::class)
    } as T?

inline fun <reified T> String.toTyped(): T =
    toTypedOrNull() ?: unsupportedType(T::class)
