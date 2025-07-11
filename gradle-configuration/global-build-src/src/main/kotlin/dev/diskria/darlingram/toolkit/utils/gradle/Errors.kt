package dev.diskria.darlingram.toolkit.utils.gradle

import kotlin.reflect.KClass

fun unsupportedOperation(): Nothing =
    error("Unsupported operation")

fun unsupportedValue(value: Any?): Nothing =
    error("Unsupported value: $value")

fun unsupportedType(clazz: KClass<*>): Nothing =
    error("Unsupported type: ${clazz.qualifiedName}")

fun invalidValue(value: Any?): Nothing =
    error("Invalid value: $value")
