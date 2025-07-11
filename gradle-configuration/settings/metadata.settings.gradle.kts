import java.util.Locale

private fun Settings.putMetadata(name: String, value: Any) {
    extra[name] = value
    gradle.settingsEvaluated {
        gradle.rootProject {
            extra[name] = value
        }
    }
}

val projectSeparator = ":"
val packageSeparator = "."
val pathSeparator = "/"

val topLevelDomain = "dev"
val author = "diskria"
val namespace = topLevelDomain + packageSeparator + author

val projectName = "Darlingram"
val applicationModuleName = "darlingram-app"
val telegramApiModuleName = "telegram-api"
val telegramJNIWrapperModuleName = "telegram-jni"
val telegramResourcesWrapperModuleName = "telegram-resources"
val packageName = namespace + packageSeparator + projectName.lowercase(Locale.ROOT)

val telegramName = "Telegram"
val telegramApplicationModuleName = "telegram-app"
val telegramLibraryModuleName = "telegram-library"
val telegramLibraryModuleDirectory = "TMessagesProj"
val telegramApplicationModuleDirectory = telegramLibraryModuleDirectory + "_App"
val telegramPackageName = namespace + packageSeparator + telegramName.lowercase(Locale.ROOT)
val telegramAppIconComponentNamePrefix = "org.telegram.messenger."
val telegramDefaultAppIconComponentName = "DefaultIcon"

val toolkitName = "Toolkit"
val pluginsDirectory = "plugins"
val utilsDirectory = "utils"
val kotlinUtilsModuleName = "kotlin-utils"
val gradlePluginModuleName = "gradle-plugin"

putMetadata("projectSeparator", projectSeparator)
putMetadata("packageSeparator", packageSeparator)
putMetadata("pathSeparator", pathSeparator)

putMetadata("author", author)

putMetadata("projectName", projectName)
putMetadata("applicationModuleName", applicationModuleName)
putMetadata("telegramApiModuleName", telegramApiModuleName)
putMetadata("telegramJNIWrapperModuleName", telegramJNIWrapperModuleName)
putMetadata("telegramResourcesWrapperModuleName", telegramResourcesWrapperModuleName)
putMetadata("packageName", packageName)

putMetadata("telegramName", telegramName)
putMetadata("telegramApplicationModuleName", telegramApplicationModuleName)
putMetadata("telegramLibraryModuleName", telegramLibraryModuleName)
putMetadata("telegramApplicationModuleDirectory", telegramApplicationModuleDirectory)
putMetadata("telegramLibraryModuleDirectory", telegramLibraryModuleDirectory)
putMetadata("telegramPackageName", telegramPackageName)
putMetadata("telegramAppIconComponentNamePrefix", telegramAppIconComponentNamePrefix)
putMetadata("telegramDefaultAppIconComponentName", telegramDefaultAppIconComponentName)

putMetadata("toolkitName", toolkitName)
putMetadata("pluginsDirectory", pluginsDirectory)
putMetadata("utilsDirectory", utilsDirectory)
putMetadata("kotlinUtilsModuleName", kotlinUtilsModuleName)
putMetadata("gradlePluginModuleName", gradlePluginModuleName)
