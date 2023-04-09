package com.kedo.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kedo.app.entity.ModelData
import com.kedo.app.repository.SplashRepository
import com.kedo.commonlibrary.ext.request
import com.kedo.commonlibrary.viewmodel.BaseViewModel

class SplashViewModel : BaseViewModel() {

    private val _modelData = MutableLiveData<ArrayList<ModelData>>()
    val mModelData: LiveData<ArrayList<ModelData>> = _modelData
    private val mRepository by lazy {
        SplashRepository()
    }

    fun getModel(){
        request(block = {
            mRepository.getModel()
        }, success = {
            _modelData.value = it
        })
    }

}