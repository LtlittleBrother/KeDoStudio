package com.kedo.commonlibrary.ext

import android.view.View
import androidx.fragment.app.Fragment
import com.gyf.immersionbar.ImmersionBar

/**
 * @Package: com.kedo.commonlibrary.ext
 * @ClassName: BaseFragmentEXT
 * @Author: LiuTao
 * @CreateDate: 2/16/23 4:03 PM
 */

/**
 * 为标题栏paddingTop和高度增加状态栏的高度
 * */
fun Fragment.addViewPadding(vararg views: View){
    views.forEach { view ->
        ImmersionBar.setTitleBar(this,view)
    }
}