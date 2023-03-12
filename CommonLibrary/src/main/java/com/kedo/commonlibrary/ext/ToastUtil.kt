package com.kedo.commonlibrary.ext

import com.hjq.toast.Toaster

/**
 * Toast统一调用工具
 *
 * @author: zzs
 * @date: 2020/11/20
 */
public fun showToastShort(msg: CharSequence) {
    showToast(msg)
}

private fun showToast(msg: CharSequence){
    Toaster.showShort(msg)
}
