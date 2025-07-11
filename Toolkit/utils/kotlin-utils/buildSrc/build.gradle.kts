plugins {
    alias(config.plugins.kotlin.jvm)
}

dependencies {
    implementation(libs.kotlinpoet)
}

kotlin {
    jvmToolchain(config.versions.java.get().toInt())
}
