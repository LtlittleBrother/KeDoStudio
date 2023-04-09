package com.kedo.app.repository

import com.kedo.app.api.MainApi
import com.kedo.app.entity.ModelData
import com.kedo.commonlibrary.network.BaseResponse
import com.kedo.commonlibrary.network.getApi
import com.kedo.commonlibrary.repository.BaseRepository

class SplashRepository: BaseRepository() {

    private val mApi by lazy {
        getApi(MainApi::class.java)
    }

    suspend fun getModel(): BaseResponse<ArrayList<ModelData>?> {
        return executeRequest {
            mApi.getModel()
        }
    }

}