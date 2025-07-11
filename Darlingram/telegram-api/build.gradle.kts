import dev.diskria.darlingram.toolkit.utils.gradle.extensions.value
import dev.diskria.darlingram.toolkit.utils.kotlin.extensions.appendPackageName
import dev.diskria.darlingram.toolkit.utils.kotlin.extensions.toPackageName

plugins {
    alias(config.plugins.android.library)
    alias(config.plugins.kotlin.android)
    alias(toolkit.plugins.gradle)
}

private val packageName: String by rootProject.extra
private val telegramApiModuleName: String by rootProject.extra

android {
    namespace = packageName.appendPackageName(telegramApiModuleName.toPackageName())

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

    implementation(toolkit.kotlin.utils)
}
