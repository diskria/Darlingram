package dev.diskria.darlingram.toolkit.utils.kotlin.utils

enum class BracketsType(
    val openingSymbol: String,
    val closingSymbol: String
) {
    ROUND(
        Constants.Symbol.OPENING_ROUND_BRACKET,
        Constants.Symbol.CLOSING_ROUND_BRACKET
    ),
    SQUARE(
        Constants.Symbol.OPENING_SQUARE_BRACKET,
        Constants.Symbol.CLOSING_SQUARE_BRACKET
    ),
    CURLY(
        Constants.Symbol.OPENING_CURLY_BRACKET,
        Constants.Symbol.CLOSING_CURLY_BRACKET
    ),
    ANGLE(
        Constants.Symbol.OPENING_ANGLE_BRACKET,
        Constants.Symbol.CLOSING_ANGLE_BRACKET
    ),
}
