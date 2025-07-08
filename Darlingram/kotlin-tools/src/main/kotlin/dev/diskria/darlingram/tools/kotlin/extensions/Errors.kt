package dev.diskria.darlingram.tools.kotlin.extensions

import kotlin.reflect.KClass

fun unsupportedOperation(): Nothing =
    error("Unsupported operation")

fun unsupportedValue(value: Any?): Nothing =
    error("Unsupported value: $value")

fun unsupportedType(clazz: KClass<*>): Nothing =
    error("Unsupported type: ${clazz.simpleName}")
