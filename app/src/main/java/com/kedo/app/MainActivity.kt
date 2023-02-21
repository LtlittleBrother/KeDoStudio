package com.kedo.app

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.kedo.commonlibrary.activity.BaseActivity
import com.kedo.commonlibrary.ext.getCount
import com.kedo.commonlibrary.ext.saveCount
import com.kedo.studio.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private var mIsDraw: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener{
            override fun onPreDraw(): Boolean {
                Log.d("liutao","mIsDraw == $mIsDraw")
                return if (mIsDraw){
                    content.viewTreeObserver.removeOnPreDrawListener(this)
                    true
                }else{
                    false
                }
            }
        })
        var position = 0
        lifecycleScope.launch(Dispatchers.IO){
            while (position <= 3){
                Log.d("liutao","Dispatchers 》》 position == $position")
                delay(1000)
                ++position
            }
            withContext(Dispatchers.Main){
                Log.d("liutao","Dispatchers 》》 mIsDraw == $mIsDraw")
                mIsDraw = true
            }
        }
    }

    override fun initUi() {

    }

    override fun initData() {

    }

    override fun initEvent() {
        mBinding.mSaveTv.setOnClickListener {
            lifecycleScope.launch (Dispatchers.IO){
                saveCount((0..Int.MAX_VALUE).random())
            }
        }
        mBinding.mLoadTv.setOnClickListener {
            lifecycleScope.launch (Dispatchers.IO){
                val value = getCount()
                withContext(Dispatchers.Main){
                    mBinding.mCountTv.text = "获取到:$value"
                }
            }
        }
    }
}