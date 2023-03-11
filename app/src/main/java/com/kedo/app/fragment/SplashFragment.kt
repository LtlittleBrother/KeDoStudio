package com.kedo.app.fragment

import com.kedo.app.MainActivity
import com.kedo.app.databinding.FragmentSplashBinding
import com.kedo.commonlibrary.fragment.BaseFragment

class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    override fun initUi() {

    }

    override fun initData() {

    }

    override fun initEvent() {

    }

    private fun showMainTab() {
        if (activity is MainActivity) {
            activity?.let {
                (it as MainActivity).showContent()
            }
        }
//        if (UserInfoEXT.getUserSessionId().isEmpty()){
//            activity?.let { LoginActivityActivity.toLoginPage(it) }
//        }
        hideSplash()
    }

    private fun hideSplash() {
        activity?.apply {
            supportFragmentManager.beginTransaction().remove(this@SplashFragment)
                .commitAllowingStateLoss()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SplashFragment()
    }
}