package com.kedo.app

import androidx.lifecycle.lifecycleScope
import com.kedo.commonlibrary.activity.BaseActivity
import com.kedo.commonlibrary.ext.getCount
import com.kedo.commonlibrary.ext.saveCount
import com.kedo.studio.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : BaseActivity<ActivityMainBinding>() {


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