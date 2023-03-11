import com.kedo.dependencieplugin.manage.*

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
}

android {
    namespace = "com.kedo.util"
    compileSdk = BuildConfig.compileSdk

    defaultConfig {
        minSdk = BuildConfig.minSdk
        targetSdk = BuildConfig.targetSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    flavorDimensions.addAll(listOf("env"))
    productFlavors {
        create("offLine") {
            dimension = "env"
        }
        create("preLine") {
            dimension = "env"
        }
        create("onLine") {
            dimension = "env"

        }
        create("onLineArm64") {
            dimension = "env"
        }
    }


    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar"))))
    implementation(Other.kotlin_stdlib)
    implementation(AndroidX.appcompat)
    implementation(Google.material)
    implementation(Other.retrofit2_converter_gson)
    testImplementation(AndroidX.test_junit)
    androidTestImplementation(AndroidX.junit)
    androidTestImplementation(AndroidX.espresso_core)
}