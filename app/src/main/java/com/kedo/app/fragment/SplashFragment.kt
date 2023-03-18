package com.kedo.app.fragment

import android.util.Log
import androidx.fragment.app.viewModels
import com.kedo.app.MainActivity
import com.kedo.app.databinding.FragmentSplashBinding
import com.kedo.app.viewmodel.SplashViewModel
import com.kedo.commonlibrary.fragment.BaseFragment
import com.kedo.commonlibrary.manage.DownloadManage
import com.kedo.commonlibrary.manage.lsitener.CommonDownloadListener
import com.liulishuo.okdownload.DownloadTask
import com.liulishuo.okdownload.core.cause.EndCause
import java.lang.Exception

class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    private val mViewModel by viewModels<SplashViewModel>()

    private val url =
        "https://pcclient.download.youku.com/iku-win-release/youkuclient_setup_9.2.15.1002.exe"

    private val mListener: CommonDownloadListener = object : CommonDownloadListener() {
        override fun onProgress(progress: Float) {
            super.onProgress(progress)
            mBinding.mProgressBar.progress = progress.toInt()
            mBinding.mProgressTv.text = progress.toString().plus("%")
        }

        override fun taskEnd(task: DownloadTask, cause: EndCause, realCause: Exception?) {
            super.taskEnd(task, cause, realCause)
            mBinding.mProgressBar.progress = 100
            mBinding.mProgressTv.text = "100%"
        }
    }

    override fun initUi() {

    }

    override fun initData() {
        mBinding.mStartTv.setOnClickListener {
            mViewModel.getModel()
//            DownloadManage.instant()
//                .startSingleDownloadTask(url, "AndroidStudio.exe", true, mListener)
        }
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