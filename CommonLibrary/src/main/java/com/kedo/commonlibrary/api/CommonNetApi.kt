package com.kedo.commonlibrary.api

import com.kedo.commonlibrary.entity.BaseHttpBean
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * @Package: com.blank.commonlibrary.api
 * @ClassName: CommonApi
 * @Author: LiuTao
 * @CreateDate: 2022/8/22 14:39
 */
interface CommonNetApi {

    @GET
    suspend fun getBiaDu(@Url url: String): BaseHttpBean<String>

}