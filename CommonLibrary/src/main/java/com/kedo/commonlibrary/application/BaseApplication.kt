package com.kedo.commonlibrary.application

import android.app.Application
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.lifecycle.ProcessLifecycleOwner
import com.kedo.commonlibrary.lifecycle.KtxAppLifeObserver
import com.kedo.commonlibrary.lifecycle.KtxLifeCycleCallBack
import com.kedo.commonlibrary.network.manager.NetworkStateReceive

/**
 * @Package: com.kedo.commonlibrary.application
 * @ClassName: BaseApplication
 * @Author: LiuTao
 * @CreateDate: 2/8/23 11:37 AM
 */
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }

}