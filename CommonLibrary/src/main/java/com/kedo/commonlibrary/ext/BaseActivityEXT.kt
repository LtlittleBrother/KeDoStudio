package com.kedo.commonlibrary.ext

import android.app.Activity
import android.view.View
import com.gyf.immersionbar.ImmersionBar

/**
 * @Package: com.kedo.commonlibrary.ext
 * @ClassName: BaseActivityEXT
 * @Author: LiuTao
 * @CreateDate: 2/16/23 4:02 PM
 */

/**
 * 为标题栏paddingTop和高度增加状态栏的高度
 * */
fun Activity.addViewPadding(vararg views: View){
    views.forEach { view ->
        ImmersionBar.setTitleBar(this,view)
    }
}