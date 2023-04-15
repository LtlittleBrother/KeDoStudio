package com.kedo.app.fragment

import android.util.Log
import androidx.fragment.app.viewModels
import com.blankj.utilcode.util.ZipUtils
import com.kedo.app.MainActivity
import com.kedo.app.R
import com.kedo.app.databinding.FragmentSplashBinding
import com.kedo.app.viewmodel.SplashViewModel
import com.kedo.commonlibrary.BuildConfig
import com.kedo.commonlibrary.ext.showToastShort
import com.kedo.commonlibrary.fragment.BaseFragment
import com.kedo.commonlibrary.manage.DownloadManage
import com.kedo.commonlibrary.manage.defaultPath
import com.kedo.commonlibrary.manage.lsitener.CommonDownloadListener
import com.liulishuo.okdownload.DownloadTask
import com.liulishuo.okdownload.core.cause.EndCause
import java.io.File

class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    private val mViewModel by viewModels<SplashViewModel>()

    private val mDownloadFile by lazy {
        File(defaultPath,"model.zip")
    }

    private val mUnZipFile by lazy {
        File(defaultPath,"model")
    }

    private val mListener: CommonDownloadListener = object : CommonDownloadListener() {
        override fun onProgress(progress: Float) {
            super.onProgress(progress)
            mBinding.mProgressBar.progress = progress.toInt()
            mBinding.mProgressTv.text =  resources.getString(R.string.splash_progress_text).plus("(${progress}%)")
        }

        override fun taskEnd(task: DownloadTask, cause: EndCause, realCause: Exception?) {
            super.taskEnd(task, cause, realCause)
            if (!mDownloadFile.exists()){
                return
            }
            mUnZipFile.mkdirs()
            try {
                ZipUtils.unzipFile(mDownloadFile,mUnZipFile)
                mBinding.mProgressBar.progress = 100
                mBinding.mProgressTv.text =  resources.getString(R.string.splash_progress_text).plus("(100%)")
                showMainTab()
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    override fun initUi() {

    }

    override fun initData() {
        mViewModel.getModel()
        mViewModel.mModelData.observe(this){dataList ->
            if (dataList.isNullOrEmpty()){
                showToastShort("error")
                activity?.finish()
                return@observe
            }
            val data = dataList[0]
            var downloadUrl = data.modelOneUrl
            if (downloadUrl.isNullOrEmpty()){
                showToastShort("error")
                activity?.finish()
            }
            downloadUrl = BuildConfig.BaseUrl.plus(downloadUrl)
            DownloadManage.instant().startSingleDownloadTask(downloadUrl,mDownloadFile.name, true,downloadListener = mListener)
        }
    }

    override fun initEvent() {

    }

    private fun showMainTab() {
        Log.d("liutao","showMainTab")
        if (activity is MainActivity) {
            activity?.let {
                (it as MainActivity).showContent()
            }
        }
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