private val gradleSettingsFile = "gradle-configuration/settings/main.gradle.kts"

private fun getGradleSettingsRelativePath(): File =
    generateSequence(rootProject.projectDir) { it.parentFile }
        .map { it.resolve(gradleSettingsFile) }
        .firstOrNull(File::isFile)
        ?: error("Gradle configuration not found")

apply(from = getGradleSettingsRelativePath())

private val projectSeparator = ":"

private fun String.parseTaskName(): Pair<String, String> =
    split(projectSeparator)
        .filter { it.isNotEmpty() }
        .let { parts ->
            val task = parts.lastOrNull().orEmpty()
            val module = parts.dropLast(1).lastOrNull().orEmpty()
            module to task
        }

private fun String.toModuleName(): String =
    projectSeparator + this

private val projectName: String by extra
private val telegramName: String by extra

private val applicationModule: String by extra
private val telegramApiModule: String by extra

private val kotlinToolsModule: String by extra
private val toolkitModule: String by extra

private val telegramJNIWrapperModule: String by extra
private val telegramResourcesWrapperModule: String by extra

private val telegramApplicationModule: String by extra

rootProject.name = projectName

if (startParameter.taskNames.isEmpty()) {
    includeToolsModules()
    includeToolkitModule()
    includeModules()
    includeTelegram()
} else {
    startParameter.taskNames.forEach { taskName ->
        val (module, task) = taskName.parseTaskName()
        includeToolsModules()
        includeToolkitModule()
        when (module) {
            applicationModule -> includeModules()
            telegramApplicationModule -> includeTelegram()
            else -> {
                includeModules()
                includeTelegram()
            }
        }
    }
}

private fun includeToolsModules() {
    includeBuild(kotlinToolsModule)
}

private fun includeToolkitModule() {
    includeBuild(toolkitModule)
}

private fun includeModules() {
    listOf(
        applicationModule,
        telegramApiModule,
        telegramJNIWrapperModule,
        telegramResourcesWrapperModule,
    ).forEach { module ->
        include(module.toModuleName())
    }
}

private fun includeTelegram() {
    includeBuild(rootDir.parent) { name = telegramName }
}
