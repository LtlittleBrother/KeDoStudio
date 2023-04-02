package com.kedo.app.fragment

import android.util.Log
import androidx.fragment.app.viewModels
import com.blankj.utilcode.util.EncryptUtils
import com.blankj.utilcode.util.GsonUtils
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
        "77429774C84455AEBD32844EB0D1606E81FEA51D1FA83CB40A07F3F84243B964B7D20977A2C7CAE224A1C38FA7227ACAD776EBB44F5AB4FC9CEDFDEA06AA4572D5CB86407F10D039B745DF94140D0C5F0CE105E44664B220800B72BED98D339BDCB56B1984CC34BFEDF1CD347612FC7AFF6C0140BFC73ACC3365A19B1E5DFD517977E7F3CDD76F1BD42F23212DC874B715E58C1AB7BD05B2B1D1BD78A79361A7C7469C2BB248EC344B57C1C670DE02A23EE5769746E3DCF1D746D8FE9F5377EA72E0F883632DF82F0ADF396B2711B333F86A66546BBB23909774725BADAF5F12BEED805DC5CA4D97B30817E65A49FE3901448D3CD5A4662D4B21F94EEB87E3EA96998A62BB6954FEE78EFEAD224AF622E13EEC4EA437A0B1BCC2C1F6938C125BBE3BA8987C0C16AEA726CF56BC705A68527060D19131F50EBB6855557A6FF879B7F61F46E95D53143109A8FD20279589A1BC557F19CE2591EC6657D04649EE2B363C9F0F066D8E87A8DAA4A4A4C173D619D5C11B766222E33454FDB4312559947D146EC631C8C9B7A1A61F993120A2DD05AFF08B7DF4BA8DFBB04A617F422166BA9AEBE065166FD41BEE38C249550E8365EED43A521E084A61CD128774D9C98A7AD38816B6199EAFE9F4153C64E1B2F366543E1A251C6133C482BF75F4C03AD4AB9A9AF4502AFD2B6EC6C6A5ED1803063BB6D75E07C37B221CB2262383EE18F3E9DF604822FA4114FA88DC33C2E0F034036B6473FFDDCEAEFD36BBDAB71505E353A76D53ACA085B883D486A17082230D0236AD9AE69AA767B327019179C77523"

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
        }
        mViewModel.mModelData.observe(this){
            Log.d("liutao","json == ${GsonUtils.toJson(it)}")
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