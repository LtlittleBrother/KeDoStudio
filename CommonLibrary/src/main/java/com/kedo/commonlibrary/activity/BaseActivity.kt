package com.kedo.commonlibrary.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.ktx.immersionBar
import com.kedo.commonlibrary.ext.inflateBindingWithGeneric

/**
 * @Package: com.kedo.commonlibrary.activity
 * @ClassName: BaseActivity
 * @Author: LiuTao
 * @CreateDate: 2/16/23 3:52 PM
 */
abstract class BaseActivity<T : ViewBinding> : AppCompatActivity() {

    private var _binding: T? = null
    protected val mBinding by lazy {
        _binding!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(themeId)
        super.onCreate(savedInstanceState)
        _binding = inflateBindingWithGeneric(layoutInflater)
        setContentView(mBinding.root)
        initImmersionBar()
        initUi()
        initData()
        initEvent()
    }

    abstract fun initUi()

    abstract fun initData()

    abstract fun initEvent()

    protected open val themeId = com.kedo.commonlibrary.R.style.Theme_App

    //状态栏字体是深色，不写默认为亮色
    protected open val statusBarDarkFont = true

    //导航栏图标是深色，不写默认为亮色
    protected open val navigationBarDarkIcon = true

    //隐藏状态栏或导航栏或两者，不写默认不隐藏
    protected open val hideBar = BarHide.FLAG_SHOW_BAR

    //初始化immersionBar block不为null则使用block的设置，否则使用通用配置
    protected open fun initImmersionBar(block: (ImmersionBar.() -> Unit)? = null) {
        immersionBar {
            block?.apply {
                invoke(this@immersionBar)
            } ?: kotlin.run {
                //透明状态栏，不写默认透明色
                transparentStatusBar()
                //透明导航栏，不写默认黑色(设置此方法，fullScreen()方法自动为true)
                transparentNavigationBar()
                //透明状态栏和导航栏，不写默认状态栏为透明色，导航栏为黑色（设置此方法，fullScreen()方法自动为true）
                transparentBar()
                statusBarDarkFont(statusBarDarkFont)
                navigationBarDarkIcon(navigationBarDarkIcon)
                hideBar(hideBar)
            }
        }
    }

    protected fun bindingIsValid(): Boolean {
        return _binding != null
    }

    fun showLoading(loadingMessage: String){

    }

    fun dismissLoading(){

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}