package com.kedo.app.viewmodel

import com.kedo.app.repository.SplashRepository
import com.kedo.commonlibrary.ext.request
import com.kedo.commonlibrary.viewmodel.BaseViewModel

class SplashViewModel : BaseViewModel() {

    private val mRepository by lazy {
        SplashRepository()
    }

    fun getModel(){
        request(block = {
            mRepository.getModel()
        }, success = {
            
        })
    }

}