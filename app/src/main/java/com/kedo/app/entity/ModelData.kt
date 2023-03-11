package com.kedo.app.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ModelData(
    val audioUrl: String? = null
): Parcelable
