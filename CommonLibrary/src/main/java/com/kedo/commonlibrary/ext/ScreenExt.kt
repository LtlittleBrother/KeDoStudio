package com.kedo.commonlibrary.ext

import android.content.res.Resources

/**
 * @Package: com.nine.core
 * @ClassName: ScreenExt
 * @Author: LiuTao
 * @CreateDate: 2022/6/17 11:34
 */
val screenWidth: Int
    get() = Resources.getSystem().displayMetrics.widthPixels

val screenHeight: Int
    get() = Resources.getSystem().displayMetrics.heightPixels

val Int.dp: Int
    get() {
        val scale = Resources.getSystem().displayMetrics.density
        return (this * scale + 0.5f).toInt()
    }

val Int.px: Int
    get() {
        val scale = Resources.getSystem().displayMetrics.density
        return (this / scale + 0.5f).toInt()
    }

val Float.px: Float
    get() {
        val scale = Resources.getSystem().displayMetrics.density
        return this / scale + 0.5f
    }

val Float.dp: Float
    get() {
        val scale = Resources.getSystem().displayMetrics.density
        return this * scale + 0.5f
    }