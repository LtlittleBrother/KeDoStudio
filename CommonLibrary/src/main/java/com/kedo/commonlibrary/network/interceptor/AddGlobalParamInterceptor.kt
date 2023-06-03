package com.kedo.commonlibrary.network.interceptor

import android.util.Base64
import android.util.Log
import android.webkit.WebSettings
import com.blankj.utilcode.util.EncryptUtils
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.Utils
import com.google.gson.JsonParser
import com.kedo.commonlibrary.network.AesUtils
import com.kedo.commonlibrary.network.ApiResponse
import okhttp3.*
import okio.Buffer
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import java.net.URLEncoder

/**
 * Author:Ljb
 * Time:2018/8/9
 * There is a lot of misery in life
 **/
class AddGlobalParamInterceptor : Interceptor {
    private val gson = GsonUtils.getGson()
    override fun intercept(chain: Interceptor.Chain): Response {


        //需要加签的公共参数
        val paramMap = HttpConfig.getParam()?.toMutableMap()
        //公共Header
        val headerMap = HttpConfig.getHeader()

        val oldRequest = chain.request()
        val newRequestBuilder = oldRequest.newBuilder()

        // 添加公共Header
        val newHeaderBuilder = oldRequest.headers.newBuilder()
        if (headerMap != null && headerMap.isNotEmpty()) {
            headerMap.map { newHeaderBuilder.add(it.key, it.value) }
        }

        val requestParams = hashMapOf<String, Any?>()
        if (oldRequest.method == "GET" || oldRequest.method == "DELETE") {
            //获取Get请求中的参数
            val urlBuilder = oldRequest.url
            val keys = urlBuilder.queryParameterNames
            for (key in keys) {
                requestParams[key] = urlBuilder.queryParameter(key)
            }
        } else {
            oldRequest.body?.let {
                if (it is FormBody) {
                    for (i in 0 until it.size) {
                        requestParams[it.encodedName(i)] = it.encodedValue(i)
                    }
                } else {
                    val buffer = Buffer()
                    it.writeTo(buffer)
                    val utf8 = buffer.readByteString().utf8()
                    requestParams["request_body_signature"] =
                        EncryptUtils.encryptMD5ToString(utf8, false)
                }

            }
        }

        //组装签名所需的参数
        paramMap?.let {
            paramMap["sn"] = getSignStr(it, requestParams)
        }
        newHeaderBuilder["Content-Type"] = "application/json"
        newHeaderBuilder["User-Agent"] = getUserAgent() ?: ""
        paramMap?.let {
            newHeaderBuilder["X-Req-Sig"] = getAuthStr(it)
        }
        newRequestBuilder.headers(newHeaderBuilder.build())

        val newRequest =
            newRequestBuilder.method(oldRequest.method, oldRequest.body).build()


        var originalResponse: Response = try {
            chain.proceed(newRequest)
        } catch (e: Exception) {
            e.message?.let {
                Log.d("Http Error: %s", it)
            }
            throw e
        }
        try {
            //解密服务端返回的数据
            val contentType: MediaType? = originalResponse.body?.contentType()
            val bodyString = originalResponse.peekBody(Long.MAX_VALUE).string()
            val jsonObject = JSONObject(bodyString?:"")
            var code = 0
            var msg = ""
            var dataObject: Any? = null
            if (jsonObject.has("data") && jsonObject.has("code")){
                code = jsonObject.getInt("code")
                msg = jsonObject.getString("msg")
                val data = jsonObject.getString("data")
                val responseData = AesUtils.decrypt(data.toByteArray())
                if (responseData?.isNotEmpty() == true){
                    val jsonString = String(responseData)
                    val dataJson= JSONTokener(jsonString).nextValue()
                    if (dataJson is JSONArray){
                        dataObject = JSONArray(jsonString)
                    }else if (dataJson is JSONObject){
                        dataObject = JSONObject(jsonString)
                    }
                }
            }
            val jsonParser = JsonParser()
            val parse = jsonParser.parse(dataObject.toString())
            val apiResponse = ApiResponse(code,msg, parse)
            val jsonString = GsonUtils.toJson(apiResponse)
            //生成新的ResponseBody
            val newResponseBody = ResponseBody.create(contentType, jsonString)
            //response
            originalResponse = originalResponse.newBuilder().body(newResponseBody).build()
        }catch (e: Exception){
            e.printStackTrace()
        }

        return originalResponse
    }

    private fun getUserAgent(): String {
        val userAgent: String = try {
            WebSettings.getDefaultUserAgent(Utils.getApp())
        } catch (var5: java.lang.Exception) {
            System.getProperty("http.agent") as String
        }
        val sb = StringBuffer()
        var i = 0
        val length = userAgent.length
        while (i < length) {
            val c = userAgent[i]
            if (c.code in 32..126) {
                sb.append(c)
            } else {
                sb.append(String.format("\\u%04x", Integer.valueOf(c.code)))
            }
            ++i
        }
        return sb.toString()
    }

    private fun getAuthStr(requestParams: Map<String, String>): String {
        val json = gson.toJson(requestParams)
        return Base64.encodeToString(json.toByteArray(), Base64.NO_WRAP)
    }

    private fun getSignStr(
        paramMap: Map<String, String>,
        requestParams: HashMap<String, Any?>
    ): String {
        paramMap.map {
            requestParams[it.key] = it.value
        }
        //请求参数排序
        val signParams = requestParams.toSortedMap()
        val sb = StringBuilder()
        for (entry in signParams.entries) {
            if (entry.value is String) {
                sb.append(entry.key).append("=").append(entry.value).append("&")
            } else {
                sb.append(entry.key).append("=").append(gson.toJson(entry.value)).append("&")
            }
        }
        val signStr = sb.toString().substring(0, sb.length - 1)
        return AesUtils.encrypt(EncryptUtils.encryptMD5ToString(signStr,false))
    }

    private fun encode(params: MutableMap<String, String>): Map<String, String> {
        return run {
            for ((key, value) in params) {
                try {
                    params[key] = URLEncoder.encode(value.toString(), "utf-8")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            params
        }
    }
}