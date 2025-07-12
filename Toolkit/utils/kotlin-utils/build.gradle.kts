import dev.diskria.darlingram.toolkit.utils.gradle.GenerateMetadataTask
import dev.diskria.darlingram.toolkit.utils.gradle.metadataConstants
import dev.diskria.darlingram.toolkit.utils.gradle.value

plugins {
    `maven-publish`
    alias(config.plugins.kotlin.jvm)
}

val javaVersion: Int = config.versions.java.value()

kotlin {
    jvmToolchain(javaVersion)
}

private val kotlinUtilsModuleName: String by rootProject.extra
private val packageName: String by rootProject.extra

publishing.publications.create<MavenPublication>(kotlinUtilsModuleName) {
    groupId = toolkit.versions.dev.value()
    artifactId = kotlinUtilsModuleName
    version = toolkit.versions.indev.value()
    from(components["java"])
}

private val metadataDirectory = layout.buildDirectory.dir("generated")

private val generateMetadataTask = tasks.register<GenerateMetadataTask>("generateMetadata") {
    metadataDirectory.get().asFile.mkdirs()

    outputDirectoryProperty.set(metadataDirectory)
    packageNameProperty.set(packageName)

    val metadataConstants = rootProject.extra.properties.metadataConstants()

    val projectName by metadataConstants
    val applicationModuleName by metadataConstants
    val telegramApiModuleName by metadataConstants
    val telegramJNIWrapperModuleName by metadataConstants
    val telegramResourcesWrapperModuleName by metadataConstants
    val packageName by metadataConstants

    val telegramName by metadataConstants
    val telegramApplicationModuleName by metadataConstants
    val telegramLibraryModuleName by metadataConstants
    val telegramApplicationModuleDirectory by metadataConstants
    val telegramLibraryModuleDirectory by metadataConstants
    val telegramAppIconComponentNamePrefix by metadataConstants
    val telegramDefaultAppIconComponentName by metadataConstants

    val toolkitName by metadataConstants

    metadataProperty.set(
        mapOf(
            projectName,
            applicationModuleName,
            telegramApiModuleName,
            telegramJNIWrapperModuleName,
            telegramResourcesWrapperModuleName,
            packageName,

            telegramName,
            telegramApplicationModuleName,
            telegramLibraryModuleName,
            telegramApplicationModuleDirectory,
            telegramLibraryModuleDirectory,
            telegramAppIconComponentNamePrefix,
            telegramDefaultAppIconComponentName,

            toolkitName
        )
    )
}

tasks.withType<PublishToMavenLocal>().configureEach {
    dependsOn(generateMetadataTask)
}

sourceSets["main"].kotlin.srcDir(metadataDirectory)
