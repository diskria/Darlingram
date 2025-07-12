private val GRADLE_KTS_EXTENSION = ".gradle.kts"

file(".")
    .listFiles { file ->
        file.isFile &&
                file.name.endsWith(GRADLE_KTS_EXTENSION) &&
                file.name != "main.settings" + GRADLE_KTS_EXTENSION
    }
    ?.forEach { gradleSettings ->
        apply(from = gradleSettings)
    }
