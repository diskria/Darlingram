package dev.diskria.darlingram.toolkit.submodules

import dev.diskria.darlingram.toolkit.utils.serialization.annotations.IgnoreUnknownKeys
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Suppress("UnsafeOptInUsageError")
@Serializable
@IgnoreUnknownKeys
data class IOSVersionFile(
    @SerialName("app") val versionName: String,
)
