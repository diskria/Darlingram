plugins {
    alias(config.plugins.kotlin.serialization)
    `kotlin-dsl`
}

val packageName: String by rootProject.extra
val toolkitModule: String by rootProject.extra

gradlePlugin {
    plugins {
        val pluginName = project.name
        create(pluginName) {
            id = pluginName
            version = tools.versions.indev.get()
            implementationClass = "$packageName.$toolkitModule.plugin.GradleToolkitPlugin"
        }
    }
}

dependencies {
    implementation(libs.androidx.annotations)
    implementation(libs.javapoet)
    implementation(libs.kotlinpoet)
    implementation(libs.kotlin.serialization)
    implementation(tools.kotlin)
    implementation(gradleApi())

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
