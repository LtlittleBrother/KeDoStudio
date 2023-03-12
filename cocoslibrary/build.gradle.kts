import com.kedo.dependencieplugin.manage.*
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}
kapt {
    arguments {
        arg("AROUTER_MODULE_NAME", project.name)
    }
}
android {
    namespace = "com.kedo.cocos"
    compileSdk = BuildConfig.compileSdk

    defaultConfig {
        minSdk = BuildConfig.minSdk
        targetSdk = BuildConfig.targetSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    sourceSets {
        getByName("main"){
            jniLibs.srcDirs("libs")
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
    api(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar"))))
    implementation(project(":CommonLibrary"))
    kapt(Other.a_router_compiler)
//    compileOnly("libs/android-async-http-1.4.9.jar")
//    compileOnly("libs/httpclient-4.4.1.1.jar")
//    implementation("libs/com.android.vending.expansion.zipfile.jar")
//    implementation("libs/oppoSDK.jar")
    testImplementation(AndroidX.test_junit)
    androidTestImplementation(AndroidX.junit)
    androidTestImplementation(AndroidX.espresso_core)
}