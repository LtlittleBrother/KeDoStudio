plugins {
    id("com.repo.plugin")
    id("org.jetbrains.kotlin.android") version "1.7.10" apply false
}

buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven {
            url = uri("https://jitpack.io")
        }
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.2.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.10")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://jitpack.io")
        }
    }
}

//子模块公用插件
subprojects {
    apply(plugin = "org.jetbrains.kotlin.android")
    apply(plugin = "com.repo.plugin")
    if (name == "app"){
        apply(plugin = "com.android.application")
    }else{
        apply(plugin = "com.android.library")
    }
}

tasks.create("clean", type = Delete::class){
    rootProject.buildDir
}