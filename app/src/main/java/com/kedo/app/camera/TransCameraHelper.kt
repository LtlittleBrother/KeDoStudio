package com.kedo.app.camera
import com.blankj.utilcode.util.ActivityUtils
import com.cocos.utils.Live2dManager
import com.kedo.app.MainActivity

/**
 *Author : create by Telyo on 2021/9/3 15:48
 *Desc   : 与人物合照
 */
class TransCameraHelper {

    companion object {
        private var instance: TransCameraHelper = TransCameraHelper()
        fun getInstance(): TransCameraHelper {
            return instance
        }
    }

    /**
     * 获取主activity
     */
    fun getMainActivity(): MainActivity? {
        ActivityUtils.getActivityList().forEach {
            if (it is MainActivity){
                return it
            }
        }
        return null
    }

    private fun getGlSurface(): Camera2GLSurfaceView {
        return getMainActivity()?.glSurfaceView as Camera2GLSurfaceView
    }

    fun openCamera() {
        getMainActivity()?.let {
            Live2dManager.getInstance().toCameraState()
            getGlSurface().openCamera()
        }
    }

    fun closeCamera() {
        try {
            getGlSurface().closeCamera()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        Live2dManager.getInstance().finishCameraState()
    }

    fun switchCamera() {
        getGlSurface().switchCamera()
    }

}