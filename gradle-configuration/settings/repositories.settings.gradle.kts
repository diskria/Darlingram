private fun RepositoryHandler.commonRepositories() {
    mavenLocal()
    mavenCentral()
    google()
}

private fun RepositoryHandler.pluginRepositories() {
    gradlePluginPortal()
}

dependencyResolutionManagement.repositories {
    commonRepositories()
}

pluginManagement.repositories {
    pluginRepositories()
    commonRepositories()
}
