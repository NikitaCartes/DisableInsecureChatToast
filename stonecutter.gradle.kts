plugins {
    id("dev.kikugie.stonecutter")
    id("me.modmuss50.mod-publish-plugin") version "0.8.4"
}

stonecutter active "26-fabric"

stonecutter parameters {
    constants.match(current.project.substringAfterLast('-'), "fabric", "neoforge")
}

stonecutter.tasks {
    order("publishMods")
}

publishMods {
    val githubToken = System.getenv("GITHUB_TOKEN") ?: ""
    val modVersion = property("mod_version").toString()

    dryRun = githubToken.isEmpty()
    version = modVersion
    displayName = modVersion
    changelog = rootProject.file("RELEASE_NOTE.md").readText()
    type = STABLE

    github {
        accessToken = githubToken
        repository = "NikitaCartes/DisableInsecureChatToast"
        commitish = "master"
        tagName = modVersion
        allowEmptyFiles = true
    }
}
