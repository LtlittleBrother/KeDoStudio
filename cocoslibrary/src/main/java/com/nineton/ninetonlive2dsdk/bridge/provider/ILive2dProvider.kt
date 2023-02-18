package com.nineton.ninetonlive2dsdk.bridge.provider

import android.content.Context

interface ILive2dProvider {
    fun hitInteractiveDialog(hitArea: String, modelId: Int,roleId:Int)
    fun stopAll()
    fun setVisible(visible:Boolean)
    fun setVoiceEnable(isEnable:Boolean)

    fun startGravity(modelId: Int,roleId:Int)
    fun releaseGravity()

    fun registerNetWork(context: Context)
    fun unregisterNetWork(context: Context)
}