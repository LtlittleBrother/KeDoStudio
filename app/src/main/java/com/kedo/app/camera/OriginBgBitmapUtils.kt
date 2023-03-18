package com.kedo.app.camera

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.cocos.base.Live2dHelper
import com.cocos.utils.Live2dManager

/**
 *Author : create by Telyo on 2021/9/13 11:41
 *Desc   : 拍照 透卡 还原原来的背景 需要的bitmap
 */
object OriginBgBitmapUtils {

    var bitmap: Bitmap? = null

    var lastFilePath = ""


    fun getBgBitmap(): Bitmap? {
       val  path = Live2dManager.getInstance().getBgPath()
        if ( path != lastFilePath) {
            lastFilePath = path
            bitmap = BitmapFactory.decodeFile("${Live2dHelper.defaultPath}$path")
        }
        return bitmap
    }

}