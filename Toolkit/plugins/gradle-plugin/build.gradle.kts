import java.util.Locale

plugins {
    `kotlin-dsl`
    alias(config.plugins.kotlin.serialization)
}

val packageSeparator: String by rootProject.extra

val packageName: String by rootProject.extra
val toolkitName: String by rootProject.extra
val gradlePluginModuleName: String by rootProject.extra

gradlePlugin {
    plugins {
        create(gradlePluginModuleName) {
            id = gradlePluginModuleName
            version = toolkit.versions.indev.get()
            implementationClass = listOf(
                packageName,
                toolkitName.lowercase(Locale.ROOT),
                "ToolkitPlugin"
            ).joinToString(packageSeparator)
        }
    }
}

dependencies {
    implementation(libs.androidx.annotations)
    implementation(libs.javapoet)
    implementation(libs.kotlinpoet)
    implementation(libs.kotlin.serialization)
    implementation(libs.okhttp)

    implementation(toolkit.kotlin.utils)

    compileOnly(config.android.tools)

    implementation(gradleApi())
}

kotlin {
    jvmToolchain(config.versions.java.get().toInt())
}
