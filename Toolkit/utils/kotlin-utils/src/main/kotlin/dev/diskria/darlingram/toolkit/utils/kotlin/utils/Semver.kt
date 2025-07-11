package dev.diskria.darlingram.toolkit.utils.kotlin.utils

import dev.diskria.darlingram.toolkit.utils.kotlin.extensions.orZero

data class Semver(
    val major: Int,
    val minor: Int,
    val patch: Int
) {
    override fun toString(): String = "$major.$minor.$patch"

    companion object {
        fun parse(versionName: String): Semver {
            val parts = versionName.split(Constants.Symbol.DOT).mapNotNull { it.toIntOrNull() }
            return Semver(
                parts.getOrNull(0).orZero(),
                parts.getOrNull(1).orZero(),
                parts.getOrNull(2).orZero()
            )
        }
    }
}
