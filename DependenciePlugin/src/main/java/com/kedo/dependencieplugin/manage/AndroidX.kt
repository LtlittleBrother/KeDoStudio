package com.kedo.dependencieplugin.manage

/**
 * @Package: com.study.dependencies
 * @ClassName: AndroidX
 * @Author: LiuTao
 * @CreateDate: 2022/8/12 15:06
 */
object AndroidX {
    const val core_ktx = "androidx.core:core-ktx:1.9.0"
    const val appcompat = "androidx.appcompat:appcompat:1.6.1"
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:2.1.4"
    const val test_junit = "androidx.test.ext:junit:1.1.3"
    const val junit = "junit:junit:4.13.2"
    const val espresso_core = "androidx.test.espresso:espresso-core:3.4.0"
    const val multidex = "androidx.multidex:multidex:2.0.1"
    const val splash_screen = "androidx.core:core-splashscreen:1.0.0"

    //jetpack
    const val activity = "androidx.activity:activity-ktx:1.5.1"
    const val fragment = "androidx.fragment:fragment-ktx:1.5.1"
    const val lifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:${Version.lifecycle_version}"
    const val lifecycle_viewmodel_ktx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.lifecycle_version}"
    const val lifecycle_livedata_ktx = "androidx.lifecycle:lifecycle-livedata-ktx:${Version.lifecycle_version}"
    const val jetpack_lifecycle_process = "androidx.lifecycle:lifecycle-process:${Version.lifecycle_version}"
    const val paging = "androidx.paging:paging-runtime:3.1.1"
    const val transition = "androidx.transition:transition-ktx:1.4.1"
    const val datastore_preferences  = "androidx.datastore:datastore-preferences:${Version.datastore_version}"
    const val datastore_core = "androidx.datastore:datastore-core:${Version.datastore_version}"
    const val room_runtime = "androidx.room:room-runtime:${Version.room_version}"
    const val room_compiler = "androidx.room:room-compiler:${Version.room_version}"
}