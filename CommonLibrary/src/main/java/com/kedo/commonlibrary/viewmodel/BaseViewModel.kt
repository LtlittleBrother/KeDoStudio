package com.kedo.commonlibrary.viewmodel

import androidx.lifecycle.ViewModel
import com.kedo.commonlibrary.livedata.event.EventLiveData

/**
 * @Package: com.kedo.commonlibrary.viewmodel
 * @ClassName: BaseViewModel
 * @Author: LiuTao
 * @CreateDate: 2/17/23 2:49 PM
 */
open class BaseViewModel: ViewModel() {

    val loadingChange: UiLoadingChange by lazy { UiLoadingChange() }

    /**
     * 内置封装好的可通知Activity/fragment 显示隐藏加载框 因为需要跟网络请求显示隐藏loading配套才加的，不然我加他个鸡儿加
     */
    inner class UiLoadingChange {
        //显示加载框
        val showDialog by lazy { EventLiveData<String>() }
        //隐藏
        val dismissDialog by lazy { EventLiveData<Boolean>() }
    }

}