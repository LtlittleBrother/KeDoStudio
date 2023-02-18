package com.cocos.base

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Part(var texture: String, var position: Int) : Parcelable