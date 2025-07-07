import dev.diskria.darlingram.toolkit.extensions.value
import dev.diskria.darlingram.toolkit.plugin.tasks.CleanCMakeCacheTask
import dev.diskria.darlingram.toolkit.plugin.tasks.ExportAPKTask
import dev.diskria.darlingram.toolkit.plugin.tasks.getDisplayName
import dev.diskria.darlingram.tools.kotlin.utils.Constants

plugins {
    alias(config.plugins.android.app)
    alias(config.plugins.kotlin.android)
    alias(tools.plugins.toolkit)
}

private val packageName: String by rootProject.extra
private val telegramApiModule: String by rootProject.extra
private val telegramJNIWrapperModule: String by rootProject.extra
private val telegramResourcesWrapperModule: String by rootProject.extra

android {
    namespace = packageName
    compileSdk = config.versions.compile.sdk.value()

    defaultConfig {
        applicationId = packageName

        minSdk = config.versions.min.sdk.value()
        targetSdk = config.versions.target.sdk.value()

        versionCode = app.versions.code.value()
        versionName = app.versions.name.value()
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        buildConfig = true
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

    lint {
        disable += "OldTargetApi"
    }
}

dependencies {
    coreLibraryDesugaring(config.desugaring)

    implementation(libs.androidx.core)
    implementation(libs.androidx.annotations)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(tools.kotlin)

    listOf(
        telegramJNIWrapperModule,
        telegramApiModule,
        telegramResourcesWrapperModule
    ).forEach {
        implementation(project(Constants.Symbol.COLON + it))
    }
}

tasks.matching { it.name.startsWith("assemble") }.configureEach {
    finalizedBy(tasks.named(ExportAPKTask::class.getDisplayName()))
}

tasks.named("clean") {
    finalizedBy(tasks.named(CleanCMakeCacheTask::class.getDisplayName()))
}
