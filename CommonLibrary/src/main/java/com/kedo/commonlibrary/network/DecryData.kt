package com.kedo.commonlibrary.network

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DecryData(
    val code: Int = 0,
    val msg: String? = null,
    var data: String? = null
): Parcelable
