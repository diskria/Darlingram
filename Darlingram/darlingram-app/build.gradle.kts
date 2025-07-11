import dev.diskria.darlingram.toolkit.utils.gradle.extensions.value

plugins {
    alias(config.plugins.android.app)
    alias(config.plugins.kotlin.android)
    alias(toolkit.plugins.gradle.plugin)
}

private val projectSeparator: String by rootProject.extra

private val packageName: String by rootProject.extra
private val telegramApiModuleName: String by rootProject.extra
private val telegramJNIWrapperModuleName: String by rootProject.extra
private val telegramResourcesWrapperModuleName: String by rootProject.extra

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

    implementation(toolkit.kotlin.tools)

    listOf(
        telegramApiModuleName,
        telegramJNIWrapperModuleName,
        telegramResourcesWrapperModuleName
    ).forEach { moduleName ->
        implementation(project(projectSeparator + rootProject.name + projectSeparator + moduleName))
    }
}
