package com.cocos.utils

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Matrix
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.GLUtils
import com.cocos.base.Live2dHelper
import com.cocos.base.Part
import com.kedo.commonlibrary.application.BaseApplication
import com.kedo.commonlibrary.ext.appContext
import com.nineton.ninetonlive2dsdk.bridge.utils.VisualizerPlayer
import org.cocos2dx.lib.Cocos2dxGLSurfaceView
import java.io.File
import java.util.*
import javax.microedition.khronos.opengles.GL10

class Live2dManager {
    private var jsonPath: String = ""
    private var modelPath: String = ""
    private var bgPath: String = ""
    private var glSurfaceView: GLSurfaceView? = null

    private var isCanUseLive = false
    private fun initLive2d(parentPath: String = Live2dHelper.defaultPath) {
        Live2dHelper.setBackground(bgPath)
        Live2dHelper.setModelPath(modelPath)
        Live2dHelper.setJsonPath(jsonPath)
        Live2dHelper.setParentPath(parentPath)
        isCanUseLive = true
    }


    //initSurface
    fun initSurface(glSurfaceView: GLSurfaceView) {
        if (this.glSurfaceView == null) {
            this.glSurfaceView = glSurfaceView
        }
    }

    fun refreshModel(
        modelPath: String,
        jsonPath: String,
        bgPath: String, parentPath: String = Live2dHelper.defaultPath
    ) {
        this.modelPath = modelPath
        this.bgPath = bgPath
        this.jsonPath = jsonPath
        initLive2d(parentPath)
        refreshModel()
    }

    fun refreshModel() {
        if (!isCanUseLive) return
        glSurfaceView?.queueEvent {
            Live2dHelper.changeModel(0)
        }
        reSetPosition()
    }

    fun stopLive2d() {
        if (!isCanUseLive) return
        glSurfaceView?.queueEvent {
            Live2dHelper.stopLive2d()
        }
    }

    fun getBgPath():String{
        return bgPath
    }

    fun reSetPosition() {
        if (!isCanUseLive) return
        moveTo(0f, 0f, true)
        scaleModel(0.5f, true)
    }

    fun restartLive2d() {
        if (!isCanUseLive) return
        glSurfaceView?.queueEvent {
            Live2dHelper.restartLive2d()
        }
    }

    companion object {

        fun getInstance(): Live2dManager {
            return Holder.instance
        }

        fun getJsonName(modelPath: String): String {
            return File(appContext.filesDir.absolutePath + "/live2d/" + modelPath).listFiles()
                ?.firstOrNull {
                    it.name.endsWith("model3.json")
                }?.name ?: ""
        }

        fun getTextureFilePath(modelPath: String): String {
            return "$modelPath/${
                File(appContext.filesDir.absolutePath + "/live2d/" + modelPath).listFiles()
                    ?.first {
                        it.name.contains(".") && it.isDirectory
                    }?.name ?: ""
            }/"
        }
    }
    private object Holder {
        @SuppressLint("StaticFieldLeak")
        val instance = Live2dManager()
    }

    /**
     * 换装
     */
    fun changeDress(parts: List<Part>) {
        if (!isCanUseLive) return
        glSurfaceView?.queueEvent {
            if (!parts.isNullOrEmpty())
                Live2dHelper.changeDress(parts)
        }
    }

    fun cancelDress(position: Int) {
        glSurfaceView?.queueEvent {
            Live2dHelper.cancelDress(position)
        }
    }

    fun setIsRestoreEye(isRestoreEye: Boolean) {
        glSurfaceView?.queueEvent {
           ( glSurfaceView as Cocos2dxGLSurfaceView).isRestoreEye(isRestoreEye)
        }
    }

    fun attentionModel(isMan: Boolean) {
        if (!isCanUseLive) return
        glSurfaceView?.queueEvent {
            Live2dHelper.setBackground(bgPath)
            startMotionCircle("m3")
            glSurfaceView?.queueEvent {
                if (isMan) {
                    moveTo(0f, -0.9f)
                    scaleModel(1.7f)
                } else {
                    moveTo(0f, -0.8f)
                    scaleModel(1.8f)
                }
            }
        }
    }

    fun changeBackground(bgPath: String) {
        if (!isCanUseLive) return
        this.bgPath = bgPath
        glSurfaceView?.queueEvent {
            Live2dHelper.setBackground(bgPath)
        }
    }

    fun toCameraState() {
        glSurfaceView?.queueEvent {
            Live2dHelper.setBackground("/assetslive2d/default.png")
        }
    }

    fun finishCameraState() {
        glSurfaceView?.queueEvent {
            Live2dHelper.setBackground(bgPath)
        }
    }

    /**
     * 模型缩放 原始值为1.0
     */
    private fun scaleModel(scale: Float, isSave: Boolean = false) {
        Live2dHelper.scaleTo(scale, isSave)
    }

    /**
     * 调整模型位置
     */
    private fun moveTo(x: Float, y: Float, isSave: Boolean = false) {
        Live2dHelper.moveTo(x, y, isSave)
    }

    /**
     * 引导模式下的设置
     */
    fun guildModel() {
        glSurfaceView?.queueEvent {
            moveTo(0f, -0.75f, true)
            scaleModel(1.8f, true)
        }
    }

    fun showAttentionModel(isMan: Boolean) {
        glSurfaceView?.queueEvent {
            Live2dHelper.switchAttentionModel(isMan)
        }
    }

    fun restorePosition() {
        glSurfaceView?.queueEvent {
            Live2dHelper.restorePosition()
        }
    }

    /**
     * 半身模式
     * 重置调用时 持久化
     */
    fun toHalf(isMan: Boolean, isSave: Boolean = false) {
        glSurfaceView?.queueEvent {
            if (isMan) {
                moveTo(0f, -0.8f, isSave)
                scaleModel(1.7f, isSave)
            } else {
                moveTo(0f, -0.7f, isSave)
                scaleModel(1.8f, isSave)
            }
        }
    }

    fun resetModel(isSave: Boolean = false){
        moveTo(0f, -0f, isSave)
        scaleModel(1f, isSave)
    }

    /**
     * 商城打开状态下的全身模式
     */
    fun toHole(isMan: Boolean) {
        glSurfaceView?.queueEvent {
            if (isMan) {
                moveTo(0f, 0.3f)
                scaleModel(0.55f)
            } else {
                moveTo(0f, 0.3f)
                scaleModel(0.6f)
            }
        }
    }

    fun toMemoryModel(isMan: Boolean) {
//        glSurfaceView?.queueEvent {
//            if (isMan) {
//                moveTo(0f, 0.1f)
//                scaleModel(0.9f)
//            } else {
//                moveTo(0f, 0.3f)
//                scaleModel(1.0f)
//            }
//        }
        toHalf(isMan)
    }

    /**
     * 正常情况下的全身模式
     */
    fun toLiveHole(isMan: Boolean, isSave: Boolean = true) {
        glSurfaceView?.queueEvent {
            if (isMan) {
                moveTo(0f, 0f, isSave)
                scaleModel(0.83f, isSave)
            } else {
                moveTo(0f, 0f, isSave)
                scaleModel(0.93f, isSave)
            }
        }
    }

    /**
     * 传入名字为截图（用于套装封面） 不传入则为 拍照
//     */
//    fun takePicture(modelPath: String, index: Int = -1) {
//        if (!isCanUseLive) return
//        clearPictures()
//        val fileName = if (index == -1) {
//            modelPath
//        } else {
//            "${modelPath}/${UserInfoSp.getUserInfo()?.id ?: 1}/$index/role.png"
//        }
//        File(Live2dHelper.defaultPath + fileName).parentFile?.apply {
//            if (!exists())
//                mkdirs()
//        }
//        Live2dHelper.takePicture(fileName)
//    }

    private fun clearPictures() {
        File(appContext.filesDir.absolutePath + "/live2d/").listFiles()?.forEach {
            if (it.name.startsWith("share_") && it.name.endsWith("jpg")) {
                it.delete()
            }
        }
    }

    /**
     * 执行动作
     */
    fun startMotion(group: String = "AllMotions", motion: Int = 0, priority: Int = 3) {
        if (!isCanUseLive) return
        glSurfaceView?.queueEvent {
            Live2dHelper.startMotion(group, motion, priority)
        }
    }

    /**
     * 执行动作
     */
    fun startMotionCircle(group: String = "AllMotions") {
        if (!isCanUseLive) return

        glSurfaceView?.queueEvent {
            Live2dHelper.startMotionCircle(group)
        }
    }

    fun stopMotionCircle() {
        if (!isCanUseLive) return

        glSurfaceView?.queueEvent {
            Live2dHelper.stopMotionCircle()
        }
    }

    /**
     * 播放语言 不循环
     * @param bgm 音频文件的绝对路径
     */
    fun setBackgroundMusic(bgm: String) {
        glSurfaceView?.queueEvent {
            Live2dHelper.setBackgroundMusic(bgm)
        }
    }

    /**
     * 播放语言 不循环
     * @param bgm 音频文件的绝对路径
     */
    fun playWarnEffect(bgm: String) {
        glSurfaceView?.queueEvent {
            Live2dHelper.setBackgroundMusic(bgm)
        }
    }

    /**
     * @param parts
     */
    fun setParts(parts: List<Part>) {
        if (parts == null)
            return
        Live2dHelper.setDress(parts)
    }

    /**
     * 设置背景音乐
     *      * @param bgm 音频文件的绝对路径
     */
    fun playBackgroundMusic(bgm: String) {
        glSurfaceView?.queueEvent {
            Live2dHelper.playBackgroundMusic(appContext.filesDir.absolutePath + "/live2d/" + bgm)
        }
    }

    fun stopBackgroundMusic() {
        glSurfaceView?.queueEvent {
            Live2dHelper.stopBackgroundMusic()
        }
    }

    fun setCocosTouchListener(listener: Live2dHelper.CocosTouchListener) {
        Live2dHelper.setTouchListener(listener)
    }

    fun setLoadListener(loader: Live2dHelper.LoadListener) {
        Live2dHelper.setLoadListener(loader)
    }


    fun showSnow(file: String) {
        glSurfaceView?.queueEvent {
            Live2dHelper.startSnow(file)
        }
    }

    private var mouthVar = 0
    private var flag = true
    private val temp = 5
    private val random = Random()
//    private var disposable: Disposable? = null

    /**
     * 说话
     */
//    fun speakNow(speakTime: Long = 0) {
//        disposable?.dispose()
//        Observable.interval(100, 10, TimeUnit.MILLISECONDS).subscribe({
//            if (speakTime != 0L && it * 10 >= speakTime) {
//                shutMouth()
//                disposable?.dispose()
//                return@subscribe
//            }
//            glSurfaceView?.queueEvent {
//                if (random.nextBoolean()) {
//                    return@queueEvent
//                }
//                if (flag)
//                    mouthVar += temp
//                else
//                    mouthVar -= temp
//                if (mouthVar % 100 == 0) {
//                    if (flag) {
//                        mouthVar -= temp
//                    } else {
//                        mouthVar += temp
//                    }
//                    flag = !flag
//                }
//                Live2dHelper.setMouthOpen(mouthVar / 100.0f, mouthVar % 100.0f)
//            }
//        }, {}, {}, {
//            disposable = it
//        })
//    }

    /**
     * 闭嘴
     */
    fun shutMouth() {
        glSurfaceView?.queueEvent {
            Live2dHelper.setMouthOpen(0.05f, 0.2f)
        }
//        disposable?.dispose()
    }

    fun recordScreen(gl: GL10, textureId: Int, w: Int, h: Int) {
        GlBitmapUtils.screenShot(gl, 0, 0, w, h)
            .apply {
                createTexture(this, textureId)
            }
    }

    private fun createTexture(bmptexture: Bitmap, textureId: Int): Int {
        try {
            val mt = Matrix()
            mt.postRotate(360f)
            mt.postScale(-1f, 1f)
            val bt =
                Bitmap.createBitmap(bmptexture, 0, 0, bmptexture.width, bmptexture.height, mt, true)
            //生成纹理
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId)
            //设置缩小过滤为使用纹理中坐标最接近的一个像素的颜色作为需要绘制的像素颜色
            GLES20.glTexParameteri(
                GLES20.GL_TEXTURE_2D,
                GLES20.GL_TEXTURE_MIN_FILTER,
                GLES20.GL_NEAREST
            )
            //设置放大过滤为使用纹理中坐标最接近的若干个颜色，通过加权平均算法得到需要绘制的像素颜色
            GLES20.glTexParameteri(
                GLES20.GL_TEXTURE_2D,
                GLES20.GL_TEXTURE_MAG_FILTER,
                GLES20.GL_LINEAR
            )
            //设置环绕方向S，截取纹理坐标到[1/2n,1-1/2n]。将导致永远不会与border融合
            GLES20.glTexParameteri(
                GLES20.GL_TEXTURE_2D,
                GLES20.GL_TEXTURE_WRAP_S,
                GLES20.GL_CLAMP_TO_EDGE
            )
            //设置环绕方向T，截取纹理坐标到[1/2n,1-1/2n]。将导致永远不会与border融合
            GLES20.glTexParameteri(
                GLES20.GL_TEXTURE_2D,
                GLES20.GL_TEXTURE_WRAP_T,
                GLES20.GL_CLAMP_TO_EDGE
            )
            //根据以上指定的参数，生成一个2D纹理
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bmptexture, 0)
        } catch (e: Exception) {
        }
        return textureId
    }

    fun setBackgroundVolume(volume: Float) {
        glSurfaceView?.queueEvent {
            Live2dHelper.setBackgroundMusicVolume(volume)
        }
    }

    fun restart(){
        glSurfaceView?.queueEvent{
            Live2dHelper.restartLive2d()
        }
    }
    fun setEditModel(isEdit: Boolean) {
        glSurfaceView?.queueEvent {
            Live2dHelper.setEditModel(isEdit)
        }
    }

    fun setEffectVolume(volume: Float) {
        glSurfaceView?.queueEvent {
            VisualizerPlayer.getInstance().setMusicVolume(volume)
        }
    }

    /**
     * 检查模型的纹理 数量
     */
    private val baseFilePath by lazy {
        appContext.filesDir.absolutePath + "/live2d/"
    }

    fun getTextureSize(modelId: Int): Int {
//        val motionsFile = "${baseFilePath}model_${modelId}/motions"
        val motionsFile = "${baseFilePath}model_${modelId}"
        val file = File(motionsFile)
        if (!file.exists()) return 8
        for (f in file.listFiles() ?: arrayOf()) {
            if (
                f.name.contains("1024")
                || f.name.contains("2048")
                || f.name.contains("4096")
            ) {
                return f.listFiles()?.size?.minus(1) ?: 8
            }
        }
        return 8
    }

    fun releaseSurface() {
        this.glSurfaceView = null
    }
}