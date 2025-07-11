import dev.diskria.darlingram.tools.gradle.GenerateMetadataTask
import dev.diskria.darlingram.tools.gradle.metadataConstants

plugins {
    `maven-publish`
    alias(config.plugins.kotlin.jvm)
}

private val kotlinToolsModuleName: String by rootProject.extra
private val packageName: String by rootProject.extra

publishing.publications.create<MavenPublication>(kotlinToolsModuleName) {
    groupId = toolkit.versions.dev.get()
    artifactId = kotlinToolsModuleName
    version = toolkit.versions.indev.get()
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
