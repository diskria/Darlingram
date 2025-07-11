package dev.diskria.darlingram.toolkit.utils.kotlin.utils

class Constants {

    object File {

        object Path {
            const val CURRENT_DIRECTORY: String =
                Symbol.DOT + Symbol.FORWARD_SLASH
        }

        object Extension {
            const val PROPERTIES: String = "properties"
            const val APK: String = "apk"
            const val JSON: String = "json"
            const val XML: String = "xml"
            const val TL: String = "tl"
            const val TXT: String = "txt"
        }
    }

    object Symbol {
        const val DOT: String = "."
        const val COLON: String = ":"
        const val SPACE: String = " "
        const val UNDERSCORE: String = "_"
        const val COMMA: String = ","
        const val EMPTY: String = ""
        const val FORWARD_SLASH: String = "/"
        const val NEW_LINE: String = "\n"

        const val SINGLE_QUOTE: String = "\'"
        const val DOUBLE_QUOTE: String = "\""

        const val OPENING_ROUND_BRACKET: String = "("
        const val CLOSING_ROUND_BRACKET: String = ")"
        const val OPENING_SQUARE_BRACKET: String = "["
        const val CLOSING_SQUARE_BRACKET: String = "]"
        const val OPENING_CURLY_BRACKET: String = "{"
        const val CLOSING_CURLY_BRACKET: String = "}"
        const val OPENING_ANGLE_BRACKET: String = "<"
        const val CLOSING_ANGLE_BRACKET: String = ">"

        const val SCHEME_SEPARATOR: String = "://"
        const val PACKAGE_SEPARATOR: String = DOT
        const val GRADLE_PROJECT_SEPARATOR: String = COLON
        const val PATH_SEPARATOR: String = FORWARD_SLASH
    }

    object Web {
        const val HTTP_SCHEME: String = "http"
        const val HTTPS_SCHEME: String = "https"

        const val HTTP_URL_PREFIX: String = HTTP_SCHEME + Symbol.SCHEME_SEPARATOR
        const val HTTPS_URL_PREFIX: String = HTTPS_SCHEME + Symbol.SCHEME_SEPARATOR
    }

    object VersionControlSystem {
        const val GIT: String = "git"
    }
}
