package dev.diskria.darlingram.toolkit

import dev.diskria.darlingram.toolkit.tasks.android.AndroidToolkitTask
import dev.diskria.darlingram.toolkit.tasks.gradle.GradleToolkitTask
import dev.diskria.darlingram.toolkit.tasks.registerSealedTasks
import dev.diskria.darlingram.toolkit.utils.gradle.extensions.getAndroidSdkShell
import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("unused")
class ToolkitPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        if (project.getAndroidSdkShell() != null) {
            project.registerSealedTasks(AndroidToolkitTask::class)
            project.registerSealedTasks(GradleToolkitTask::class)
        }
    }
}
