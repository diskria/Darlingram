package dev.diskria.darlingram.toolkit.tasks

import dev.diskria.darlingram.Metadata
import dev.diskria.darlingram.toolkit.utils.ProjectDirectories
import dev.diskria.darlingram.toolkit.utils.gradle.extensions.directories
import dev.diskria.darlingram.toolkit.utils.gradle.extensions.getDisplayName
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import kotlin.reflect.KClass

abstract class ToolkitTask(
    @Internal val groupNamePrefix: String,
    @Internal val taskDescription: String,
) : DefaultTask() {

    final override fun getDescription(): String =
        taskDescription

    final override fun getGroup(): String =
        listOf(
            Metadata.TOOLKIT_NAME,
            groupNamePrefix
        ).joinToString("-").lowercase()

    @Internal
    val directories: ProjectDirectories = project.directories()

    @Internal
    protected abstract fun runTask(directories: ProjectDirectories)

    @TaskAction
    private fun action() {
        runTask(directories)
    }
}

fun <T : ToolkitTask> Project.registerSealedTasks(baseClass: KClass<T>) {
    baseClass.sealedSubclasses.forEach { taskClass ->
        tasks.register(taskClass.getDisplayName(), taskClass.java)
    }
}
