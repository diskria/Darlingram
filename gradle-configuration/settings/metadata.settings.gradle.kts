import java.util.Locale

private fun Settings.putMetadata(name: String, value: Any) {
    extra[name] = value
    gradle.settingsEvaluated {
        gradle.rootProject {
            extra[name] = value
        }
    }
}

val author = "diskria"

val projectName = "Darlingram"
val telegramName = "Telegram"
val packageName = "dev.$author.${projectName.lowercase(Locale.ROOT)}"

val applicationModule = "presentation"
val telegramApiModule = "telegram-api"

val kotlinToolsModule = "kotlin-tools"
val toolkitModule = "toolkit"

val telegramJNIWrapperModule = "telegram-jni"
val telegramResourcesWrapperModule = "telegram-resources"

val telegramLibraryModule = "TMessagesProj"
val telegramApplicationModule = telegramLibraryModule + "_App"

putMetadata("author", author)

putMetadata("projectName", projectName)
putMetadata("telegramName", telegramName)
putMetadata("packageName", packageName)

putMetadata("applicationModule", applicationModule)
putMetadata("telegramApiModule", telegramApiModule)

putMetadata("kotlinToolsModule", kotlinToolsModule)
putMetadata("toolkitModule", toolkitModule)

putMetadata("telegramJNIWrapperModule", telegramJNIWrapperModule)
putMetadata("telegramResourcesWrapperModule", telegramResourcesWrapperModule)

putMetadata("telegramLibraryModule", telegramLibraryModule)
putMetadata("telegramApplicationModule", telegramApplicationModule)
