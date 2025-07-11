package dev.diskria.darlingram.toolkit.submodules

import dev.diskria.darlingram.toolkit.utils.kotlin.extensions.wrap
import dev.diskria.darlingram.toolkit.utils.kotlin.utils.BracketsType
import dev.diskria.darlingram.toolkit.utils.kotlin.utils.Constants
import dev.diskria.darlingram.toolkit.utils.kotlin.utils.Semver

data class ClientVersion(
    val code: Int,
    val name: Semver,
) {
    fun getDisplayName(): String =
        name.toString() + Constants.Symbol.SPACE + code.toString().wrap(BracketsType.ROUND)

    companion object {
        fun parse(code: Int, name: String): ClientVersion =
            ClientVersion(code, Semver.parse(name))
    }
}
