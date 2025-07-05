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

private val forkName: String by extra
private val upstreamName: String by extra

private val forkApplicationModule: String by extra

private val kotlinToolsModule: String by extra
private val toolkitModule: String by extra

private val jniWrapperModule: String by extra
private val apiWrapperModule: String by extra
private val upstreamResourcesWrapperModule: String by extra

private val upstreamApplicationModule: String by extra

rootProject.name = forkName

if (startParameter.taskNames.isEmpty()) {
    includeToolsModules()
    includeToolkit()
    includeForkModules()
    includeUpstreamProject()
} else {
    startParameter.taskNames.forEach { taskName ->
        val (module, task) = taskName.parseTaskName()
        if (task == "publishToMavenLocal") {
            include(module.toModuleName())
        } else {
            includeToolsModules()
            includeToolkit()
            when (module) {
                forkApplicationModule -> includeForkModules()
                upstreamApplicationModule -> includeUpstreamProject()
                else -> {
                    includeForkModules()
                    includeUpstreamProject()
                }
            }
        }
    }
}

private fun includeToolsModules() {
    include(kotlinToolsModule.toModuleName())
}

private fun includeToolkit() {
    includeBuild(toolkitModule)
}

private fun includeForkModules() {
    listOf(
        forkApplicationModule,
        jniWrapperModule,
        apiWrapperModule,
        upstreamResourcesWrapperModule,
    ).forEach { module ->
        include(module.toModuleName())
    }
}

private fun includeUpstreamProject() {
    includeBuild(rootDir.parent) { name = upstreamName }
}
