fun getRelativePath(rootDir: File): File =
    generateSequence(rootDir) { it.parentFile }
        .map { it.resolve("gradle-configuration/settings/main.settings.gradle.kts") }
        .firstOrNull(File::isFile)
        ?: error("File not found")

apply(from = getRelativePath(rootDir))
