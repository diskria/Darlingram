package dev.diskria.darlingram.toolkit.utils.gradle.shell

import dev.diskria.darlingram.tools.kotlin.extensions.asDirectoryOrThrow
import dev.diskria.darlingram.tools.kotlin.extensions.modify
import dev.diskria.darlingram.tools.kotlin.extensions.toFile
import dev.diskria.darlingram.tools.kotlin.utils.Constants
import dev.diskria.darlingram.tools.kotlin.utils.Shell
import java.io.File

class GitShell private constructor(gitProjectDirectory: File) {

    private val shell: Shell = Shell.Companion.open(gitProjectDirectory)

    fun fetch(remoteName: String = ORIGIN_REMOTE_NAME) {
        executeAndGetOutput("fetch $remoteName")
    }

    fun checkout(branchName: String) {
        executeAndGetOutput("checkout $branchName")
    }

    fun pull(remoteName: String = ORIGIN_REMOTE_NAME, branchName: String) {
        executeAndGetOutput("pull $remoteName $branchName")
    }

    fun getRemoteUrl(remoteName: String): String =
        executeAndGetOutput("remote get-url $remoteName")

    fun addRemote(name: String, url: String) {
        executeAndGetOutput("remote add $name $url")
    }

    fun getAheadCount(remoteName: String, branchName: String): Int =
        executeAndGetOutput("rev-list HEAD..$remoteName/$branchName --count").toInt()

    fun hasUncommitedChanges(): Boolean =
        execute("diff --quiet").not() || execute("diff --cached --quiet").not()

    fun merge(remoteName: String, branchName: String): Boolean =
        execute("merge $remoteName $branchName")

    fun getTotalCommitsCount(): Int =
        executeAndGetOutput("rev-list --count HEAD").toInt()

    fun getHooksDirectory(): File =
        executeAndGetOutput("rev-parse --git-path hooks").toFile()

    fun initSubmodule(shouldReinit: Boolean = false) {
        if (shouldReinit) {
            executeAndGetOutput("submodule deinit --all --force")
        }
        executeAndGetOutput("submodule init")
    }

    fun updateSubmodule(allowRecursive: Boolean = false) {
        executeAndGetOutput("submodule update --init".modify {
            if (allowRecursive) this + " --recursive"
            else this
        })
    }

    private fun executeAndGetOutput(command: String): String =
        shell.runAndGetOutput("${Constants.VersionControlSystem.GIT} $command")

    private fun execute(command: String): Boolean =
        shell.run("${Constants.VersionControlSystem.GIT} $command")

    companion object {
        const val UPSTREAM_REMOTE_NAME = "upstream"
        const val ORIGIN_REMOTE_NAME = "origin"

        fun open(gitProjectDirectory: File): GitShell =
            GitShell(gitProjectDirectory.asDirectoryOrThrow())
    }
}