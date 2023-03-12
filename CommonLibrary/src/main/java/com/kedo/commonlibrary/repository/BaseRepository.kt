package com.kedo.commonlibrary.repository

import com.kedo.commonlibrary.network.BaseResponse


open class BaseRepository {
    suspend fun <T : Any> executeRequest(
        block: suspend () -> BaseResponse<T?>
    ): BaseResponse<T?> {
        return block.invoke()
    }
}