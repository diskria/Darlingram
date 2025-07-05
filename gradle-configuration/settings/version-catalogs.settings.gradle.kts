private val TOML_EXTENSION = ".toml"
private val VERSION_CATALOGS_EXTENSION = ".versions" + TOML_EXTENSION

private fun MutableVersionCatalogContainer.fromDirectory(directory: File) {
    directory.listFiles()
        ?.filter { it.isDirectory }
        ?.forEach { catalogDir ->
            catalogDir.listFiles()
                ?.firstOrNull { it.name.endsWith(VERSION_CATALOGS_EXTENSION) }
                ?.let { tomlFile ->
                    create(catalogDir.name) {
                        from(files(tomlFile))
                    }
                }
        }
}

dependencyResolutionManagement.versionCatalogs {
    fromDirectory(file("../version-catalogs"))
}
