// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.autonomousapps.dependency-analysis") version "1.30.0"
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.kotlin.ksp) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.serialization) apply false
}