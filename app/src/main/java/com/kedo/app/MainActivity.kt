package com.kedo.app

import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.kedo.app.databinding.ActivityMainBinding
import com.kedo.commonlibrary.activity.BaseActivity
import com.kedo.commonlibrary.arouter.RouterHelper

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
    }

    override fun initUi() {
        RouterHelper.instant().toLogin()
    }

    override fun initData() {

    }

    override fun initEvent() {

    }
}