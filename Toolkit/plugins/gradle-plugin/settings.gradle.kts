private val gradleSettingsFile = "gradle-configuration/settings/main.gradle.kts"

private fun getGradleSettingsRelativePath(): File =
    generateSequence(rootProject.projectDir) { it.parentFile }
        .map { it.resolve(gradleSettingsFile) }
        .firstOrNull(File::isFile)
        ?: error("Gradle configuration not found")

apply(from = getGradleSettingsRelativePath())
