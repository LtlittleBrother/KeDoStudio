package com.kedo.mine.activity

import android.view.View
import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.kedo.commonlibrary.activity.BaseActivity
import com.kedo.commonlibrary.arouter.ARouterPath
import com.kedo.mine.R
import com.kedo.mine.databinding.ActivityLogInLayoutBinding
import com.kedo.mine.viewmodel.LogInViewModel

@Route(path = ARouterPath.MINE_LOG_IN_PATH)
class LogInActivity: BaseActivity<ActivityLogInLayoutBinding>(),View.OnClickListener {

    private val mViewModel by viewModels<LogInViewModel>()

    override fun initUi() {
        mBinding.mToAccountLogIvTv.setOnClickListener(this)
        mBinding.mToGoogleLonInTv.setOnClickListener(this)
    }

    override fun initData() {

    }

    override fun initEvent() {

    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.mToAccountLogIvTv -> {

            }
            R.id.mToGoogleLonInTv -> {

            }
        }
    }
}