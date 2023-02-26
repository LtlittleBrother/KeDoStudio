package com.kedo.commonlibrary.arouter

import com.alibaba.android.arouter.launcher.ARouter

class RouterHelper {

    companion object {
        fun instant() = RouterHelperHolder.holder
    }

    private object RouterHelperHolder {
        val holder = RouterHelper()
    }

    public fun toLogin() {
        ARouter.getInstance().build(ARouterPath.MINE_LOG_IN_PATH)
            .navigation()
    }

}