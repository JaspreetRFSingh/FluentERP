// Top-level build file — configuration common to all subprojects.
plugins {
    id("com.android.application") version "8.7.3" apply false
}

tasks.register<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}
