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

    companion object {
        lateinit var mApplication: Application
        private var mNetworkStateReceive: NetworkStateReceive? = null
        var watchActivityLife = true
        var watchAppLife = true
    }

    override fun onCreate() {
        super.onCreate()

    }


    private fun install(application: Application) {
        mApplication = application
        mNetworkStateReceive = NetworkStateReceive()
        mApplication.registerReceiver(
            mNetworkStateReceive,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
        if (watchActivityLife) application.registerActivityLifecycleCallbacks(KtxLifeCycleCallBack())
        if (watchAppLife) ProcessLifecycleOwner.get().lifecycle.addObserver(KtxAppLifeObserver)
    }

}