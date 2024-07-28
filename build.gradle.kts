buildscript {
    dependencies {
        // TODO: Remove after moving to KSP.
        //noinspection UseTomlInstead
        classpath("com.neenbedankt.gradle.plugins:android-apt:1.8")
    }
}

plugins {
    alias(libs.plugins.androidGradlePlugin) apply false
    alias(libs.plugins.kotlin.android) apply false

}

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}