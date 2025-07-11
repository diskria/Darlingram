package dev.diskria.darlingram.toolkit.tasks.gradle

import dev.diskria.darlingram.toolkit.tasks.ToolkitTask

sealed class GradleToolkitTask(taskDescription: String) : ToolkitTask("gradle", taskDescription)
