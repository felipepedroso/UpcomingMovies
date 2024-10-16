import com.diffplug.gradle.spotless.SpotlessExtension
import com.diffplug.gradle.spotless.SpotlessPlugin

plugins {
    alias(libs.plugins.androidGradlePlugin) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.spotless) apply false
}

subprojects {
    apply<SpotlessPlugin>()

    configure<SpotlessExtension> {
        kotlin {
            target("**/*.kt")
            targetExclude("${layout.buildDirectory}/**/*.kt")
            ktlint()
            trimTrailingWhitespace()
            indentWithSpaces()
            endWithNewline()
        }

        kotlinGradle {
            target("**/*.kts")
            ktlint()
            trimTrailingWhitespace()
            indentWithSpaces()
            endWithNewline()
        }

        format("xml") {
            target("**/*.xml")
            indentWithSpaces()
            trimTrailingWhitespace()
            endWithNewline()
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}
