package dev.diskria.darlingram.toolkit.utils.serialization

import dev.diskria.darlingram.toolkit.utils.serialization.annotations.IgnoreUnknownKeys
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.File
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation

val jsonCache: MutableMap<KClass<*>, Json> = ConcurrentHashMap()

@Suppress("JSON_FORMAT_REDUNDANT")
@OptIn(ExperimentalSerializationApi::class)
inline fun <reified T> File.serialize(): T =
    jsonFor<T>().decodeFromStream(inputStream())

inline fun <reified T> jsonFor(): Json =
    jsonCache.getOrPut(T::class) {
        Json {
            if (T::class.shouldIgnoreUnknownKeys()) {
                ignoreUnknownKeys = true
            }
        }
    }

fun KClass<*>.shouldIgnoreUnknownKeys(): Boolean =
    findAnnotation<IgnoreUnknownKeys>() != null
