import com.android.SdkConstants
import dev.diskria.darlingram.toolkit.utils.gradle.extensions.directories
import dev.diskria.darlingram.toolkit.utils.gradle.extensions.value
import dev.diskria.darlingram.toolkit.utils.kotlin.extensions.appendPackageName
import dev.diskria.darlingram.toolkit.utils.kotlin.extensions.toPackageName

plugins {
    alias(config.plugins.android.library)
    alias(toolkit.plugins.gradle)
}

val packageName: String by rootProject.extra
val telegramResourcesWrapperModuleName: String by rootProject.extra

android {
    namespace = packageName.appendPackageName(telegramResourcesWrapperModuleName.toPackageName())

    compileSdk = config.versions.compile.sdk.value()

    defaultConfig {
        minSdk = config.versions.min.sdk.value()
    }

    sourceSets[SdkConstants.FD_MAIN].apply {
        project
            .directories()
            .getTelegramLibraryModule()
            .resolve(SdkConstants.FD_SOURCES)
            .resolve(SdkConstants.FD_MAIN)
            .let { mainDirectory ->
                mapOf(
                    assets to SdkConstants.FD_ASSETS,
                    res to SdkConstants.FD_RES
                ).forEach { target, source ->
                    target.srcDir(file(mainDirectory.resolve(source)))
                }
            }
    }

    lint {
        disable += listOf(
            "MissingTranslation",
            "ExtraTranslation"
        )
    }
}

dependencies {
    implementation(libs.appcompat)
}
