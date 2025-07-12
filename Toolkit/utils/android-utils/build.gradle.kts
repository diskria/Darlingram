import dev.diskria.darlingram.toolkit.utils.gradle.value

plugins {
    alias(config.plugins.android.library)
    alias(config.plugins.kotlin.android)
}

private val packageName: String by rootProject.extra

android {
    namespace = packageName

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
}
