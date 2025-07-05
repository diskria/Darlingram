import dev.diskria.darlingram.toolkit.extensions.buildConfig
import dev.diskria.darlingram.toolkit.extensions.createGradleTaskRunCommand
import dev.diskria.darlingram.toolkit.extensions.directories
import dev.diskria.darlingram.toolkit.extensions.value
import dev.diskria.darlingram.toolkit.platforms.ClientVersionExtractor
import dev.diskria.darlingram.toolkit.platforms.PlatformType
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
private val kotlinToolsModule: String by rootProject.extra
private val jniWrapperModule: String by rootProject.extra
private val apiWrapperModule: String by rootProject.extra
private val upstreamResourcesWrapperModule: String by rootProject.extra

android {
    namespace = packageName
    compileSdk = config.versions.compile.sdk.value()

    defaultConfig {
        applicationId = packageName

        minSdk = config.versions.min.sdk.value()
        targetSdk = config.versions.target.sdk.value()

        versionCode = app.versions.code.value()
        versionName = app.versions.name.value()

        val directories = directories()
        buildConfig(
            "UPSTREAM_VERSION",
            ClientVersionExtractor
                .extract(directories, PlatformType.ANDROID, directories.getUpstreamRoot())
                .getDisplayName()
        )
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
    implementation(libs.androidx.core)
    implementation(libs.androidx.annotations)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(tools.kotlin)

    listOf(
        jniWrapperModule,
        apiWrapperModule,
        upstreamResourcesWrapperModule
    ).forEach {
        implementation(project(Constants.Symbol.COLON + it))
    }
}

tasks.matching { it.name.startsWith("assemble") }.configureEach {
    finalizedBy(tasks.named(ExportAPKTask::class.getDisplayName()))
}

tasks.named("preBuild") {
    dependsOn(createGradleTaskRunCommand(kotlinToolsModule, "generateMetadata"))
}

tasks.named("clean") {
    finalizedBy(tasks.named(CleanCMakeCacheTask::class.getDisplayName()))
}
