package com.kedo.commonlibrary.network.converter

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class MyGsonConverterFactory : Converter.Factory() {

    private val mGson by lazy {
        Gson()
    }

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        val adapter = mGson.getAdapter(TypeToken.get(type))
        return ResponseBodyConverter(mGson,adapter)
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<out Annotation>,
        methodAnnotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody> {
        val adapter = mGson.getAdapter(TypeToken.get(type))
        return GsonRequestBodyConverter(mGson,adapter)
    }


}