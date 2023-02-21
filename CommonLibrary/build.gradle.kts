import com.kedo.dependencieplugin.manage.*
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.repo.plugin")
}
kapt {
    arguments {
        arg("AROUTER_MODULE_NAME", project.name)
    }
}
android {
    namespace = "com.kedo.commonlibrary"
    compileSdk = BuildConfig.compileSdk

    defaultConfig {
        minSdk = BuildConfig.minSdk
        targetSdk = BuildConfig.targetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    flavorDimensions.addAll(listOf("env"))
    productFlavors {
        create("offLine") {
            dimension = "env"
            ndk {
                abiFilters.add("armeabi-v7a")
                abiFilters.add("x86_64")
            }
            buildConfigField("String","BaseUrl","\"https://u.y.qq.com\"")
        }
        create("preLine") {
            dimension = "env"
            ndk {
                abiFilters.add("armeabi-v7a")
                abiFilters.add("x86_64")
            }
            buildConfigField("String","BaseUrl","\"https://u.y.qq.com\"")
        }
        create("onLine") {
            dimension = "env"
            ndk {
                abiFilters.add("armeabi-v7a")
                abiFilters.add("x86_64")
            }
            buildConfigField("String","BaseUrl","\"https://u.y.qq.com\"")
        }
        create("onLineArm64") {
            dimension = "env"
            ndk {
                abiFilters.add("armeabi-v7a")
                abiFilters.add("x86_64")
            }
            buildConfigField("String","BaseUrl","\"https://u.y.qq.com\"")
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
    api(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar"))))
    api(Google.material)
    api(AndroidX.multidex)
    api(AndroidX.core_ktx)
    api(AndroidX.appcompat)
    api(AndroidX.activity)
    api(AndroidX.fragment)
    api(AndroidX.constraintlayout)
    api(AndroidX.lifecycle)
    api(AndroidX.lifecycle_viewmodel_ktx)
    api(AndroidX.lifecycle_livedata_ktx)
    api(AndroidX.jetpack_lifecycle_process)
    api(AndroidX.paging)
    api(AndroidX.transition)
    api(AndroidX.splash_screen)
    api(AndroidX.datastore_preferences)
    api(AndroidX.datastore_core)
    annotationProcessor(AndroidX.room_compiler)
    api(AndroidX.room_runtime)

    api(Other.adapter)
    api(Other.retrofit2)
    api(Other.retrofit2_converter_gson)
    api(Other.okhttp)
    api(Other.okhttp_interceptor)
    api(Other.util_code)
    api(Other.immersion_bar)
    api(Other.immersion_bar_kts)
    api(Other.a_router)
    api(Other.unPeekLivedata)
    api(Other.retrofitUrlManager)
    kapt(Other.a_router_compiler)

    testImplementation(AndroidX.test_junit)
    androidTestImplementation(AndroidX.junit)
    androidTestImplementation(AndroidX.espresso_core)
}