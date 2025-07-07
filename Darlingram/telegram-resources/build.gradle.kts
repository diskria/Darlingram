import com.android.SdkConstants
import dev.diskria.darlingram.toolkit.extensions.directories
import dev.diskria.darlingram.toolkit.extensions.value
import dev.diskria.darlingram.tools.kotlin.extensions.appendPackageName
import dev.diskria.darlingram.tools.kotlin.extensions.toPackageName

plugins {
    alias(config.plugins.android.library)
    alias(tools.plugins.toolkit)
}

val packageName: String by rootProject.extra
val telegramResourcesWrapperModule: String by rootProject.extra

android {
    namespace = packageName.appendPackageName(telegramResourcesWrapperModule.toPackageName())

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
