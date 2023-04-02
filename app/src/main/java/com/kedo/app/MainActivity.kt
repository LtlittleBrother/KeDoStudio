package com.kedo.app

import android.content.pm.ActivityInfo
import android.graphics.PixelFormat
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.contains
import com.cocos.base.Live2dHelper
import com.cocos.utils.Live2dManager
import com.gyf.immersionbar.ImmersionBar
import com.kedo.app.camera.Camera2GLSurfaceView
import com.kedo.app.databinding.ActivityMainBinding
import com.kedo.app.ext.showSplashFragment
import com.kedo.commonlibrary.manage.defaultPath
import org.cocos2dx.lib.Cocos2dxActivity
import org.cocos2dx.lib.Cocos2dxGLSurfaceView

class MainActivity : Cocos2dxActivity(), Live2dHelper.LoadListener{

    //live2d管理器
    val manager by lazy {
        Live2dManager.getInstance()
    }

    val mBinding by lazy {
        ActivityMainBinding.inflate(LayoutInflater.from(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        super.onCreate(savedInstanceState)
        ImmersionBar.with(this).statusBarDarkFont(true, 0f).init()
        showSplashFragment()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val lp = window.attributes
            lp.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            window.attributes = lp
        }
    }

    override fun init() {
        super.init()
        setContentView(mBinding.root)
//        refreshModel()
    }

    fun showContent() {

    }

    fun refreshModel(){
        startDrawSurfaceFrame()
        Log.d("liutao","defaultPath == $defaultPath")
        manager.refreshModel("model", "huanzhuang.model3.json", "defaultBack.png",
            parentPath = defaultPath
        )
    }

    fun startDrawSurfaceFrame() {
        //绘制live2d surface 里的逻辑
        if (!mBinding.dressRootView.contains(mFrameLayout)) {
            mBinding.dressRootView.post {
                mBinding.dressRootView.addView(mFrameLayout, 0)
            }
            manager.initSurface(glSurfaceView)
            lifecycle.addObserver(glSurfaceView)
        }
    }

    override fun onCreateView(): Cocos2dxGLSurfaceView {
        val glSurfaceView = Camera2GLSurfaceView(this)
        glSurfaceView.holder.setFormat(PixelFormat.TRANSLUCENT)
        val chooser = Cocos2dxEGLConfigChooser(mGLContextAttrs)
        glSurfaceView.setEGLConfigChooser(chooser)
        glSurfaceView.setZOrderOnTop(true)
        glSurfaceView.setZOrderMediaOverlay(true)
        return glSurfaceView
    }

    override fun onDestroy() {
        super.onDestroy()
        manager.changeBackground(Live2dHelper.defaultPath)
        manager.releaseSurface()
    }

    override fun loadSuccess() {

    }
}