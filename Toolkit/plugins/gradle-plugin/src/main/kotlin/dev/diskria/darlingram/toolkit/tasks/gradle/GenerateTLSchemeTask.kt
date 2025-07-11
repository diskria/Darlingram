package dev.diskria.darlingram.toolkit.tasks.gradle

import com.palantir.javapoet.FieldSpec
import com.palantir.javapoet.JavaFile
import com.palantir.javapoet.TypeSpec
import dev.diskria.darlingram.Metadata
import dev.diskria.darlingram.toolkit.utils.ProjectDirectories
import dev.diskria.darlingram.toolkit.utils.kotlin.extensions.readByLines
import java.io.File
import javax.lang.model.element.Modifier

@Suppress("unused")
abstract class GenerateTLSchemeTask : GradleToolkitTask(
    "Generate TL scheme"
) {
    private val layerVersionRegex: Regex = Regex("""//\s*LAYER\s+(\d+)""")

    override fun runTask(directories: ProjectDirectories) {
        val apiScheme: File = directories.getTelegramApiScheme()
        require(apiScheme.exists()) {
            "${apiScheme.name} file not found at ${apiScheme.absolutePath}"
        }

        var layerVersion: Int? = null
        apiScheme.readByLines { line ->
            layerVersionRegex.find(line)?.groupValues?.get(1)?.toInt()?.let {
                layerVersion = it
            }
        }
        if (layerVersion == null) {
            error("Cannot get layer version")
        }

        val javaCodegenRoot = directories.getTLSchemeCodegen()
        javaCodegenRoot.mkdirs()

        val apiConstantsClass = TypeSpec.classBuilder("ApiConstants")
            .addModifiers(Modifier.PUBLIC)
            .addField(
                FieldSpec
                    .builder(
                        Integer::class.javaPrimitiveType,
                        "LAYER_VERSION",
                        Modifier.PUBLIC,
                        Modifier.STATIC,
                        Modifier.FINAL
                    )
                    .initializer(layerVersion.toString())
                    .build()
            )
            .build()

        JavaFile.builder(Metadata.PACKAGE_NAME, apiConstantsClass).build().writeTo(javaCodegenRoot)
    }
}
