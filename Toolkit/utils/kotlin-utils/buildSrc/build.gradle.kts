plugins {
    alias(config.plugins.kotlin.jvm)
}

dependencies {
    implementation(libs.kotlinpoet)
}

apply(from = "global.build.src.gradle.kts")
