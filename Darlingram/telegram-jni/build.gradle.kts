import com.android.SdkConstants
import dev.diskria.darlingram.toolkit.utils.PropertyNames
import dev.diskria.darlingram.toolkit.utils.gradle.extensions.directories
import dev.diskria.darlingram.toolkit.utils.gradle.extensions.getLocalProperty
import dev.diskria.darlingram.toolkit.utils.gradle.extensions.value
import dev.diskria.darlingram.toolkit.utils.kotlin.extensions.appendPackageName
import dev.diskria.darlingram.toolkit.utils.kotlin.extensions.fileName
import dev.diskria.darlingram.toolkit.utils.kotlin.extensions.splitByComma
import dev.diskria.darlingram.toolkit.utils.kotlin.extensions.toPackageName
import dev.diskria.darlingram.toolkit.utils.kotlin.utils.Constants

plugins {
    alias(config.plugins.android.library)
    alias(toolkit.plugins.gradle)
}

val packageName: String by rootProject.extra
val telegramJNIWrapperModuleName: String by rootProject.extra

android {
    namespace = packageName.appendPackageName(telegramJNIWrapperModuleName.toPackageName())

    compileSdk = config.versions.compile.sdk.value()
    ndkVersion = config.versions.ndk.value()

    val cmakeVersion: String = config.versions.cmake.value()

    defaultConfig {
        minSdk = config.versions.min.sdk.value()

        @Suppress("UnstableApiUsage")
        externalNativeBuild.cmake {
            version = cmakeVersion

            val cmakeArguments: String = config.versions.args.cmake.value()
            arguments += cmakeArguments

            val abiFiltersList = getLocalProperty(PropertyNames.ABI_FILTERS)
                ?: config.versions.default.abis.value()
            abiFilters.addAll(abiFiltersList.splitByComma().map(String::trim))
        }
    }

    externalNativeBuild.cmake {
        version = cmakeVersion
        path = project
            .directories()
            .getTelegramLibraryModule()
            .resolve(SdkConstants.FD_JNI)
            .resolve(
                fileName("CMakeLists", Constants.File.Extension.TXT)
            )
    }
}

dependencies {
    implementation(toolkit.kotlin.utils)
}
