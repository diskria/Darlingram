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
    val forkName by metadataConstants
    val upstreamName by metadataConstants
    val packageName by metadataConstants
    val forkApplicationModule by metadataConstants
    val jniWrapperModule by metadataConstants
    val apiWrapperModule by metadataConstants
    val upstreamLibraryModule by metadataConstants
    val upstreamApplicationModule by metadataConstants

    metadataProperty.set(
        mapOf(
            forkName,
            upstreamName,
            packageName,
            forkApplicationModule,
            jniWrapperModule,
            apiWrapperModule,
            upstreamLibraryModule,
            upstreamApplicationModule,
        )
    )
}

tasks.withType<PublishToMavenLocal>().configureEach {
    dependsOn(generateMetadataTask)
}

sourceSets["main"].kotlin.srcDir(metadataDirectory)
