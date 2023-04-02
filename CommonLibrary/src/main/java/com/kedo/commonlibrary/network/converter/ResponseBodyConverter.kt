package com.kedo.commonlibrary.network.converter

import com.blankj.utilcode.util.EncryptUtils
import com.blankj.utilcode.util.GsonUtils
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.kedo.commonlibrary.network.ApiResponse
import com.kedo.commonlibrary.network.BaseResponse
import com.kedo.commonlibrary.network.DecryData
import okhttp3.ResponseBody
import retrofit2.Converter

class ResponseBodyConverter<T>(
    private val mGson: Gson,
    val adapter: TypeAdapter<T>
): Converter<ResponseBody, T> {

    private val key = "UcLAFLxsZidSX0wM"
    private val padding = "AES/CBC/PKCS7Padding"
    private val iv = "1016449182158477"

    override fun convert(value: ResponseBody): T? {
        val responseJson = value.string()
        val decryData = mGson.fromJson(responseJson, DecryData::class.java)
        var newJsonString = responseJson
        if (decryData != null && decryData.data?.isNotEmpty() == true){
            val byteArray = EncryptUtils.decryptHexStringAES(decryData.data,key.toByteArray(),
                padding,iv.toByteArray())
            decryData.data = String(byteArray)
            newJsonString = GsonUtils.toJson(decryData)
        }
        try {
            return mGson.fromJson(newJsonString,ApiResponse::class.java) as? T
        }catch (e: Exception){
            e.printStackTrace()
        }finally {
            value.close()
        }
        return null
    }
}