import com.kedo.dependencieplugin.manage.*

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}
kapt {
    arguments {
        arg("AROUTER_MODULE_NAME", project.name)
    }
}
android {
    namespace = "com.kedo.app"
    compileSdk = BuildConfig.compileSdk

    defaultConfig {
        applicationId = BuildConfig.applicationId
        minSdk = BuildConfig.minSdk
        targetSdk = BuildConfig.targetSdk
        versionCode = BuildConfig.versionCode
        versionName = BuildConfig.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create(SignConfig.RELEASE_CONFIG) {
            keyAlias = SignConfig.keyAlias
            keyPassword = SignConfig.keyPassword
            storeFile = file(SignConfig.storeFile)
            storePassword = SignConfig.storePassword
            enableV1Signing = true
            enableV2Signing = true
        }

        create(SignConfig.DEBUG_CONFIG) {
            keyAlias = SignConfig.keyAlias
            keyPassword = SignConfig.keyPassword
            storeFile = file(SignConfig.storeFile)
            storePassword = SignConfig.storePassword
            enableV1Signing = true
            enableV2Signing = true
        }
    }

    buildTypes {
        release {
//            isDebuggable = true
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig =
                signingConfigs.getByName(SignConfig.RELEASE_CONFIG)
        }

        debug {
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig =
                signingConfigs.getByName(SignConfig.DEBUG_CONFIG)
        }
    }

    flavorDimensions.addAll(listOf("env"))
    productFlavors {
        create("offLine") {
            dimension = "env"
            ndk {
                abiFilters.add("armeabi-v7a")
            }
        }
        create("preLine") {
            dimension = "env"
            ndk {
                abiFilters.add("armeabi-v7a")
            }
        }
        create("onLine") {
            dimension = "env"
            ndk {
                abiFilters.add("armeabi-v7a")
            }
        }
        create("onLineArm64") {
            dimension = "env"
            ndk {
                abiFilters.add("arm64-v8a")
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(project(":CommonLibrary"))
    implementation(project(":cocoslibrary"))
    implementation(project(":mine"))
    kapt(Other.a_router_compiler)
    testImplementation(AndroidX.test_junit)
    androidTestImplementation(AndroidX.junit)
    androidTestImplementation(AndroidX.espresso_core)
}