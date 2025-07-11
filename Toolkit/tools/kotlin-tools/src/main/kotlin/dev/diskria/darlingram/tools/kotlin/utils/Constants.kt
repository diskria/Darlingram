package dev.diskria.darlingram.tools.kotlin.utils

class Constants {

    object File {

        const val CURRENT_DIRECTORY = "."

        object Extension {
            const val PROPERTIES = "properties"
            const val APK = "apk"
            const val JSON = "json"
            const val XML = "xml"
            const val TL = "tl"
            const val TXT = "txt"
        }
    }

    object Symbol {
        const val DOT = "."
        const val COLON = ":"
        const val SPACE = " "
        const val UNDERSCORE = "_"
        const val COMMA = ","
        const val EMPTY = ""
        const val FORWARD_SLASH = "/"
        const val NEW_LINE = "\n"

        const val SINGLE_QUOTE = "\'"
        const val DOUBLE_QUOTE = "\""

        const val OPENING_ROUND_BRACKET = "("
        const val CLOSING_ROUND_BRACKET = ")"
        const val OPENING_SQUARE_BRACKET = "["
        const val CLOSING_SQUARE_BRACKET = "]"
        const val OPENING_CURLY_BRACKET = "{"
        const val CLOSING_CURLY_BRACKET = "}"
        const val OPENING_ANGLE_BRACKET = "<"
        const val CLOSING_ANGLE_BRACKET = ">"

        const val SCHEME_SEPARATOR = "://"
        const val PACKAGE_SEPARATOR = DOT
        const val GRADLE_PROJECT_SEPARATOR = COLON
        const val PATH_SEPARATOR = FORWARD_SLASH
    }

    object Web {
        const val HTTP_SCHEME = "http"
        const val HTTPS_SCHEME = "https"

        const val HTTP_URL_PREFIX = HTTP_SCHEME + Symbol.SCHEME_SEPARATOR
        const val HTTPS_URL_PREFIX = HTTPS_SCHEME + Symbol.SCHEME_SEPARATOR
    }

    object VersionControlSystem {
        const val GIT = "git"
    }
}
