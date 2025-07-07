package dev.diskria.darlingram.toolkit.plugin

import dev.diskria.darlingram.Metadata
import dev.diskria.darlingram.toolkit.plugin.tasks.GradleToolkitTask
import dev.diskria.darlingram.toolkit.plugin.tasks.getDisplayName
import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("unused")
class GradleToolkitPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        if (project.name == Metadata.APPLICATION_MODULE ||
            project.name == Metadata.TELEGRAM_APPLICATION_MODULE
        ) {
            GradleToolkitTask::class.sealedSubclasses.forEach { taskClass ->
                project.tasks.register(taskClass.getDisplayName(), taskClass.java)
            }
        }
    }
}
