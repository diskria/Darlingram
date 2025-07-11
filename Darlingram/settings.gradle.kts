private val gradleSettingsFile = "gradle-configuration/settings/main.gradle.kts"

private fun getGradleSettingsRelativePath(): File =
    generateSequence(rootDir) { it.parentFile }
        .map { it.resolve(gradleSettingsFile) }
        .firstOrNull(File::isFile)
        ?: error("Gradle configuration not found")

apply(from = getGradleSettingsRelativePath())

private val projectSeparator: String by extra

private val projectName: String by extra
private val applicationModuleName: String by extra
private val telegramApiModuleName: String by extra
private val telegramJNIWrapperModuleName: String by extra
private val telegramResourcesWrapperModuleName: String by extra

private val telegramName: String by extra

private val toolkitName: String by extra
private val pluginsDirectory: String by extra
private val utilsDirectory: String by extra

private fun String.getProjectName(): String =
    split(projectSeparator)
        .filter { it.isNotEmpty() }
        .dropLast(1)
        .lastOrNull()
        .orEmpty()

rootProject.name = projectName

includeToolkit()
if (startParameter.taskNames.isEmpty()) {
    includeProjectModules()
    includeTelegram()
} else {
    startParameter.taskNames.forEach { taskName ->
        when (taskName.getProjectName()) {
            projectName -> includeProjectModules()
            telegramName -> includeTelegram()
            else -> {
                includeProjectModules()
                includeTelegram()
            }
        }
    }
}

private fun includeProjectModules() {
    listOf(
        applicationModuleName,
        telegramApiModuleName,
        telegramJNIWrapperModuleName,
        telegramResourcesWrapperModuleName,
    ).forEach { moduleName ->
        val subProjectName = projectSeparator + projectName + projectSeparator + moduleName
        include(subProjectName)
        project(subProjectName).projectDir = file(moduleName)
    }
}

private fun includeTelegram() {
    includeBuild(rootDir.parentFile) {
        name = telegramName
    }
}

private fun includeToolkit() {
    listOf(
        utilsDirectory,
        pluginsDirectory
    ).forEach { directory ->
        rootDir
            .parentFile
            .resolve(toolkitName)
            .resolve(directory)
            .listFiles()
            .filterNot { it.isHidden }
            .forEach { subProject ->
                includeBuild(subProject) {
                    val subProjectName = subProject.name.split("-").joinToString("-") {
                        it.replaceFirstChar(Char::uppercaseChar)
                    }
                    name = toolkitName + "-" + subProjectName
                }
            }
    }
}
