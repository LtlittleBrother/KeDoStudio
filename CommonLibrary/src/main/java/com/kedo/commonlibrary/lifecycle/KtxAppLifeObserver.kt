package com.kedo.commonlibrary.lifecycle

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.kedo.commonlibrary.livedata.BooleanLiveData

/**
 * 作者　: hegaojian
 * 时间　: 20120/1/7
 * 描述　:
 */
object KtxAppLifeObserver : DefaultLifecycleObserver {

    var isForeground = BooleanLiveData()


    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        isForeground.value = true
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        isForeground.value = false
    }

}