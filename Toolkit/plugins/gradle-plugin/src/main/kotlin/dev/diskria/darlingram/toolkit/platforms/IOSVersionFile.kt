package dev.diskria.darlingram.toolkit.platforms

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Suppress("UnsafeOptInUsageError")
@Serializable
data class IOSVersionFile(
    @SerialName("app") val versionName: String,
)
