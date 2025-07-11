package dev.diskria.darlingram.toolkit

import dev.diskria.darlingram.Metadata
import dev.diskria.darlingram.toolkit.tasks.android.AndroidToolkitTask
import dev.diskria.darlingram.toolkit.tasks.gradle.GradleToolkitTask
import dev.diskria.darlingram.toolkit.utils.gradle.extensions.getAndroidSdkShell
import dev.diskria.darlingram.toolkit.utils.gradle.extensions.getDisplayName
import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import kotlin.reflect.KClass

@Suppress("unused")
class GradleToolkitPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        if (project.getAndroidSdkShell() != null) {
            project.registerSealedTasks(AndroidToolkitTask::class)
        } else if (project.name.startsWith(Metadata.TOOLKIT_NAME)) {
            project.registerSealedTasks(GradleToolkitTask::class)
        }
    }

    fun <T : DefaultTask> Project.registerSealedTasks(baseClass: KClass<T>) {
        baseClass.sealedSubclasses.forEach { taskClass ->
            tasks.register(taskClass.getDisplayName(), taskClass.java)
        }
    }
}
