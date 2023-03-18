package com.kedo.commonlibrary.util

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.opengl.GLException
import android.view.View
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.nio.IntBuffer
import javax.microedition.khronos.opengles.GL10
import android.graphics.BitmapFactory
import android.net.Uri
import com.kedo.commonlibrary.ext.appContext


object BitmapUtils {
    fun saveImageToCustomDirectory(
        context: Activity,
        bmp: Bitmap,
        bitmapName: String
    ): String {
        val appDir = File(context.getExternalFilesDir("image"),"")
        if (!appDir.exists()) {
            appDir.mkdirs()
        }
        val fileName = "$bitmapName.jpg"
        val file = File(appDir, fileName)
        if (file.parentFile?.exists() == false) {
            file.parentFile?.mkdirs()
        }
        if (!file.exists()) {
            file.createNewFile()
        }
        try {
            val fos = FileOutputStream(file)
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos)
            fos.flush()
            fos.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            file.delete()
            return ""
        } catch (e: IOException) {
            e.printStackTrace()
            file.delete()
            return ""
        }
        SingleMediaScanner(context, file, null).scanFile()
        if (file.exists()) {
            return file.path
        }
        return appDir.path
    }

    fun loadBitmapFromView(v: View): Bitmap? {
        if (v.width <= 0 || v.height <= 0) return null
        val screenshot = Bitmap.createBitmap(v.width, v.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(screenshot)
//        canvas.drawFilter = PaintFlagsDrawFilter(
//            0,
//            Paint.ANTI_ALIAS_FLAG
//        )
        canvas.translate(-v.scrollX.toFloat(), -v.scrollY.toFloat())
        v.draw(canvas)// 将 view 画到画布上
        return screenshot
    }


    @Throws(OutOfMemoryError::class)
    fun createBitmapFromGLSurface(
        context: Context,
        w: Int,
        h: Int,
        gl: GL10
    ): Bitmap? {
        val bitmapBuffer = IntArray(w * h)
        val bitmapSource = IntArray(w * h)
        val intBuffer: IntBuffer = IntBuffer.wrap(bitmapBuffer)
        intBuffer.position(0)
        try {
            gl.glReadPixels(
                0, 0, w, h, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE,
                intBuffer
            )
            var offset1: Int
            var offset2: Int
            for (i in 0 until h) {
                offset1 = i * w
                offset2 = (h - i - 1) * w
                for (j in 0 until w) {
                    val texturePixel = bitmapBuffer[offset1 + j]
                    val blue = texturePixel shr 16 and 0xff
                    val red = texturePixel shl 16 and 0x00ff0000
                    val pixel = texturePixel and -0xff0100 or red or blue
                    bitmapSource[offset2 + j] = pixel
                }
            }
        } catch (e: GLException) {
            return null
        }
        return Bitmap.createBitmap(bitmapSource, w, h, Bitmap.Config.ARGB_8888)
    }

     fun getPictureSize(path:String) {

        /*第一种直接把bitmap加载到内存中，通过对bitmap的测量，
        得出宽高，由于这个方法直接把图片引入内存，
        如果图片过大，将会引发OOM；*/
        //方法一：通过uri把图片转化为bitmap的方法
        val bitmap: Bitmap = BitmapFactory.decodeFile(path)
        val height = bitmap.height
        val width = bitmap.width

        /*bitmap.options类为bitmap的裁剪类，通过他可以实现bitmap的裁剪；
        如果不设置裁剪后的宽高和裁剪比例，返回的bitmap对象将为空，
        但是这个对象存储了原bitmap的宽高信息。*/
        //方法二：使用Options类来获取
        val options: BitmapFactory.Options = BitmapFactory.Options()
        options.inJustDecodeBounds = true //这个参数设置为true才有效，
        val bmp: Bitmap = BitmapFactory.decodeFile(path, options) //这里的bitmap是个空
        val outHeight: Int = options.outHeight
        val outWidth: Int = options.outWidth
    }


    fun getPictureSize(uri: Uri):BitmapFactory.Options {

        /*第一种直接把bitmap加载到内存中，通过对bitmap的测量，
        得出宽高，由于这个方法直接把图片引入内存，
        如果图片过大，将会引发OOM；*/
        //方法一：通过uri把图片转化为bitmap的方法
//        val bitmap: Bitmap = BitmapFactory.decodeStream(AppLifecyclesImpl.appContext.contentResolver.openInputStream(uri))
//        val height = bitmap.height
//        val width = bitmap.width
//        LogUtils.debugInfo("BitmapUtils", "通过bitmap获取到的图片大小width: $width height: $height")

        /*bitmap.options类为bitmap的裁剪类，通过他可以实现bitmap的裁剪；
        如果不设置裁剪后的宽高和裁剪比例，返回的bitmap对象将为空，
        但是这个对象存储了原bitmap的宽高信息。*/
        //方法二：使用Options类来获取
        val options: BitmapFactory.Options = BitmapFactory.Options()
        options.inJustDecodeBounds = true //这个参数设置为true才有效，
        val bmp: Bitmap? = BitmapFactory.decodeStream(appContext.contentResolver.openInputStream(uri),null, options) //这里的bitmap是个空
        val outHeight: Int = options.outHeight
        val outWidth: Int = options.outWidth
        return options
    }
}