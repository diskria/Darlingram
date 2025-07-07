import dev.diskria.darlingram.tools.gradle.GenerateMetadataTask
import dev.diskria.darlingram.tools.gradle.metadataConstants

plugins {
    `maven-publish`
    alias(config.plugins.kotlin.jvm)
}

private val kotlinToolsModule: String by rootProject.extra
private val packageName: String by rootProject.extra

publishing.publications.create<MavenPublication>(kotlinToolsModule) {
    groupId = tools.versions.dev.get()
    artifactId = kotlinToolsModule
    version = tools.versions.indev.get()
    from(components["java"])
}

private val metadataDirectory = layout.buildDirectory.dir("generated")

private val generateMetadataTask = tasks.register<GenerateMetadataTask>("generateMetadata") {
    metadataDirectory.get().asFile.mkdirs()
    outputDirectoryProperty.set(metadataDirectory)

    packageNameProperty.set(packageName)

    val metadataConstants = rootProject.extra.properties.metadataConstants()
    val projectName by metadataConstants
    val telegramName by metadataConstants
    val packageName by metadataConstants
    val applicationModule by metadataConstants
    val telegramApiModule by metadataConstants
    val telegramJNIWrapperModule by metadataConstants
    val telegramLibraryModule by metadataConstants
    val telegramApplicationModule by metadataConstants

    metadataProperty.set(
        mapOf(
            projectName,
            telegramName,
            packageName,
            applicationModule,
            telegramApiModule,
            telegramJNIWrapperModule,
            telegramLibraryModule,
            telegramApplicationModule,
        )
    )
}

tasks.withType<PublishToMavenLocal>().configureEach {
    dependsOn(generateMetadataTask)
}

sourceSets["main"].kotlin.srcDir(metadataDirectory)
