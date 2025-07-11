package dev.diskria.darlingram.tools.kotlin.extensions

import dev.diskria.darlingram.tools.kotlin.utils.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.format(format: DateFormat, locale: Locale = Locale.US): String =
    SimpleDateFormat(format.pattern, locale).format(this)

fun nowDate(): Date =
    Date()

fun nowMillis(): Long =
    System.currentTimeMillis()