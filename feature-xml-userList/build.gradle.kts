plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.safeArgs)
}

android {
    namespace = "com.example.feature_xml_userlist"
    compileSdk = 36

    buildFeatures {
        viewBinding = true
    }

    defaultConfig {
        minSdk = 26
        targetSdk = 36
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.viewmodel)
    implementation(libs.activity.ktx)
    implementation(libs.fragment.ktx)
    implementation(libs.fragment.navigation)
    implementation(libs.ui.navigation)
    implementation(libs.datastore)
    implementation(project(":core-navigation"))
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)

}
