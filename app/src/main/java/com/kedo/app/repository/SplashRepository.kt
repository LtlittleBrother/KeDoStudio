package com.kedo.app.repository

import com.kedo.app.api.MainApi
import com.kedo.commonlibrary.network.getApi
import com.kedo.commonlibrary.repository.BaseRepository

class SplashRepository: BaseRepository() {

    private val mApi by lazy {
        getApi(MainApi::class.java)
    }

}