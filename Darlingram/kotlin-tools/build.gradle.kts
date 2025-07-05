import com.android.SdkConstants

plugins {
    alias(config.plugins.kotlin.jvm)
    `maven-publish`
}

publishing.publications {
    val projectName = project.name
    create<MavenPublication>(projectName) {
        groupId = tools.versions.dev.get()
        artifactId = projectName
        version = tools.versions.indev.get()
        from(components[SdkConstants.EXT_JAVA])
    }
}

private val forkName: String by rootProject.extra
private val upstreamName: String by rootProject.extra
private val packageName: String by rootProject.extra
private val forkApplicationModule: String by rootProject.extra
private val jniWrapperModule: String by rootProject.extra
private val apiWrapperModule: String by rootProject.extra
private val upstreamLibraryModule: String by rootProject.extra
private val upstreamApplicationModule: String by rootProject.extra

private val packagePath = packageName.replace(".", File.separator)
private val generatedDirectoryProvider = layout.buildDirectory.dir("generated/$packagePath")

val generateMetadataTask = tasks.register("generateMetadata") {
    inputs.property("forkName", forkName)
    inputs.property("upstreamName", upstreamName)
    outputs.dir(generatedDirectoryProvider)

    doLast {
        val generatedDirectory = generatedDirectoryProvider.get().asFile
        generatedDirectory.mkdirs()

        val className = "Metadata"
        File(generatedDirectory, className + SdkConstants.DOT_KT).writeText(
            """
            package $packageName

            object $className {
                const val FORK_NAME: String = "$forkName"
                const val UPSTREAM_NAME: String = "$upstreamName"
                const val PACKAGE_NAME: String = "$packageName"
                const val FORK_APPLICATION_MODULE: String = "$forkApplicationModule"
                const val JNI_WRAPPER_MODULE: String = "$jniWrapperModule"
                const val API_WRAPPER_MODULE: String = "$apiWrapperModule"
                const val UPSTREAM_LIBRARY_MODULE: String = "$upstreamLibraryModule"
                const val UPSTREAM_APPLICATION_MODULE: String = "$upstreamApplicationModule"
            }
            """.trimIndent()
        )
    }
}

tasks.named("compileKotlin") {
    dependsOn(generateMetadataTask)
}

tasks.withType<PublishToMavenLocal>().configureEach {
    dependsOn(generateMetadataTask)
}

sourceSets[SdkConstants.FD_MAIN].kotlin.srcDir(generatedDirectoryProvider)
