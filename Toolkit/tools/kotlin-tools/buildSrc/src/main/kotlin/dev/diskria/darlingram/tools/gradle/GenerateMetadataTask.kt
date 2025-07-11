package dev.diskria.darlingram.tools.gradle

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.TaskAction
import javax.inject.Inject

@Suppress("unused")
abstract class GenerateMetadataTask @Inject constructor(
    objects: ObjectFactory
) : DefaultTask() {

    @get:InputDirectory
    abstract val outputDirectoryProperty: DirectoryProperty

    @get:Input
    abstract val packageNameProperty: Property<String>

    @get:Input
    abstract val metadataProperty: MapProperty<String, String>

    @TaskAction
    fun generate() {
        val className = "Metadata"
        val metadataObject = TypeSpec.objectBuilder(className).addModifiers()

        metadataProperty.get().forEach { (name, value) ->
            metadataObject.addProperty(
                PropertySpec.builder(name, String::class, KModifier.CONST)
                    .initializer("%S", value)
                    .build()
            )
        }

        FileSpec
            .builder(packageNameProperty.get(), className)
            .addType(metadataObject.build())
            .build()
            .writeTo(outputDirectoryProperty.get().asFile)
    }
}
