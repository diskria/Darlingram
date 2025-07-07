import dev.diskria.darlingram.toolkit.extensions.value
import dev.diskria.darlingram.tools.kotlin.extensions.appendPackageName
import dev.diskria.darlingram.tools.kotlin.extensions.toPackageName

plugins {
    alias(config.plugins.android.library)
    alias(config.plugins.kotlin.android)
    alias(tools.plugins.toolkit)
}

private val packageName: String by rootProject.extra
private val telegramApiModule: String by rootProject.extra

android {
    namespace = packageName.appendPackageName(telegramApiModule.toPackageName())

    compileSdk = config.versions.compile.sdk.value()

    defaultConfig {
        minSdk = config.versions.min.sdk.value()
    }

    val javaVersion: Int = config.versions.java.value()

    compileOptions {
        isCoreLibraryDesugaringEnabled = true

        JavaVersion.toVersion(javaVersion).run {
            sourceCompatibility = this
            targetCompatibility = this
        }
    }

    kotlin {
        jvmToolchain(javaVersion)
    }
}

dependencies {
    coreLibraryDesugaring(config.desugaring)

    implementation(tools.kotlin)
}
