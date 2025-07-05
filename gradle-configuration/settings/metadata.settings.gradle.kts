import java.util.Locale

private fun Settings.putMetadata(name: String, value: Any) {
    extra[name] = value
    gradle.settingsEvaluated {
        gradle.rootProject {
            extra[name] = value
        }
    }
}

val author = "diskria"

val forkName = "Darlingram"
val upstreamName = "Telegram"
val packageName = "dev.$author.${forkName.lowercase(Locale.ROOT)}"

val forkApplicationModule = "presentation"

val kotlinToolsModule = "kotlin-tools"
val toolkitModule = "toolkit"

val jniWrapperModule = "telegram-jni"
val apiWrapperModule = "telegram-api"
val upstreamResourcesWrapperModule = "telegram-resources"

val upstreamLibraryModule = "TMessagesProj"
val upstreamApplicationModule = upstreamLibraryModule + "_App"

putMetadata("author", author)

putMetadata("forkName", forkName)
putMetadata("upstreamName", upstreamName)
putMetadata("packageName", packageName)

putMetadata("forkApplicationModule", forkApplicationModule)

putMetadata("kotlinToolsModule", kotlinToolsModule)
putMetadata("toolkitModule", toolkitModule)

putMetadata("jniWrapperModule", jniWrapperModule)
putMetadata("apiWrapperModule", apiWrapperModule)
putMetadata("upstreamResourcesWrapperModule", upstreamResourcesWrapperModule)

putMetadata("upstreamLibraryModule", upstreamLibraryModule)
putMetadata("upstreamApplicationModule", upstreamApplicationModule)
