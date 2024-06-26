// Top-level build file where you can add configuration options common to all sub-projects/modules.
// Common versions of plugins those used by modules are defined here.
plugins {
    // `apply false`
    // plugins with false is added as a build dependency but is not applied to the current(root) project.

    // initial plugins
    id("com.android.application") version "8.2.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
    // ksp plugin for room support
    // keep in mind that ksp version has to match kotlin version
    id("com.google.devtools.ksp") version "1.9.22-1.0.17" apply false
}