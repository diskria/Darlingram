plugins {
    `kotlin-dsl`
    alias(config.plugins.kotlin.serialization)
}

val packageName: String by rootProject.extra
val toolkitModule: String by rootProject.extra

gradlePlugin {
    plugins {
        create(toolkitModule) {
            id = toolkitModule
            version = tools.versions.indev.get()
            implementationClass = "$packageName.$toolkitModule.plugin.GradleToolkitPlugin"
        }
    }
}

dependencies {
    implementation(gradleApi())

    implementation(libs.androidx.annotations)
    implementation(libs.javapoet)
    implementation(libs.kotlinpoet)
    implementation(libs.kotlin.serialization)
    implementation(tools.kotlin)

    compileOnly(config.android.tools)
}

val javaVersion: Int = config.versions.java.get().toInt()

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(javaVersion))
    }
}

kotlin {
    jvmToolchain(javaVersion)
}
