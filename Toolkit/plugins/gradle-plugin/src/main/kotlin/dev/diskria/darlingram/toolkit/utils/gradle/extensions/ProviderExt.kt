package dev.diskria.darlingram.toolkit.utils.gradle.extensions

import dev.diskria.darlingram.toolkit.utils.kotlin.extensions.toTyped
import org.gradle.api.provider.Provider

inline fun <reified T> Provider<String>.value(): T =
    get().toTyped<T>()
