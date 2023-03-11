package com.kedo.commonlibrary.network.interceptor

import android.text.TextUtils
import android.util.Base64
import android.webkit.WebSettings
import com.blankj.utilcode.util.EncryptUtils
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.Utils
import com.kedo.commonlibrary.network.AesUtils
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
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
                    requestParams["request_body_signature"] = EncryptUtils.encryptMD5ToString(utf8,false)
                }

            }
        }

        //组装签名所需的参数
        paramMap?.let {
            paramMap["sn"] = getSignStr(it, requestParams)
        }

//        val token = UserInfoExt.getToken()
//        newHeaderBuilder["Authorization"] = if (!TextUtils.isEmpty(token)) "Bearer $token" else ""
        newHeaderBuilder["Content-Type"] = "application/json"
        newHeaderBuilder["User-Agent"] = getUserAgent() ?: ""
        paramMap?.let {
            newHeaderBuilder["X-Req-Sig"] = getAuthStr(it)
        }
        newRequestBuilder.headers(newHeaderBuilder.build())

//        loggerI("retrofit 新接口加密前：" + gson.toJson(paramMap))

        val newRequest =
            newRequestBuilder.method(oldRequest.method, oldRequest.body).build()
        try {
            return chain.proceed(newRequest)
        } catch (e: Exception) {
            throw e
        }
    }

    private fun getUserAgent(): String? {
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