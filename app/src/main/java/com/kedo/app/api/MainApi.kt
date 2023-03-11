package com.kedo.app.api

import com.kedo.app.entity.ModelData
import com.kedo.commonlibrary.network.ApiResponse
import com.kedo.commonlibrary.network.BaseResponse
import retrofit2.http.GET

interface MainApi {

    @GET("/tKdHelperModel/getModel")
    suspend fun getModel(): ApiResponse<ModelData>

}