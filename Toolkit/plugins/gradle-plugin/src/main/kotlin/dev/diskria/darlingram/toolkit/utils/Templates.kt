package dev.diskria.darlingram.toolkit.utils

object Templates {

    fun buildSubmodule(path: String, url: String): String =
        """
        [submodule "$path"]
            path = $path
            url = $url
        """.trimIndent()
}
