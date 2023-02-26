package com.kedo.commonlibrary.arouter.provider

import android.content.Context
import com.alibaba.android.arouter.facade.template.IProvider

interface IImProvider: IProvider {

    public fun initHX(context: Context)
}