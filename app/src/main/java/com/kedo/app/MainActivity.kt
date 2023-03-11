package com.kedo.app

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.cocos.base.Live2dHelper
import com.kedo.app.databinding.ActivityMainBinding
import com.kedo.app.ext.showSplashFragment
import com.kedo.commonlibrary.activity.BaseActivity
import com.kedo.commonlibrary.arouter.RouterHelper
import org.cocos2dx.lib.Cocos2dxActivity

class MainActivity : Cocos2dxActivity(), Live2dHelper.LoadListener{

    val mBinding by lazy {
        ActivityMainBinding.inflate(LayoutInflater.from(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        showSplashFragment()
    }

    fun showContent() {

    }

    override fun loadSuccess() {

    }
}