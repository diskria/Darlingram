package dev.diskria.darlingram.toolkit.platforms

import dev.diskria.darlingram.tools.kotlin.extensions.wrap
import dev.diskria.darlingram.tools.kotlin.utils.BracketsType
import dev.diskria.darlingram.tools.kotlin.utils.Constants
import dev.diskria.darlingram.tools.kotlin.utils.Semver

data class ClientVersion(
    val code: Int,
    val name: Semver
) {
    fun getDisplayName(): String =
        name.toString() + Constants.Symbol.SPACE + code.toString().wrap(BracketsType.ROUND)
}
