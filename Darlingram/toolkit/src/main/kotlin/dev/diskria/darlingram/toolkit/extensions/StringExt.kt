package dev.diskria.darlingram.toolkit.extensions

import dev.diskria.darlingram.tools.kotlin.utils.Constants

fun createGradleTaskRunCommand(moduleName: String, taskName: String): String =
    Constants.Symbol.COLON + moduleName + Constants.Symbol.COLON + taskName
