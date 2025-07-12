import org.gradle.api.tasks.SourceSetContainer
import java.io.File

fun getRelativePath(rootDir: File): File =
    generateSequence(rootDir) { it.parentFile }
        .map { it.resolve("gradle-configuration/global-build-src/src/main/kotlin") }
        .firstOrNull(File::isDirectory)
        ?: error("File not found")

val sourceSets = project.extensions.getByName("sourceSets") as SourceSetContainer

sourceSets.named("main") {
    java.srcDir(getRelativePath(rootDir))
}
