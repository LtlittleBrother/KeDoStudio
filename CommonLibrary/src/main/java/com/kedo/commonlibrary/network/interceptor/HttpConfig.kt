package com.kedo.commonlibrary.network.interceptor

import com.blankj.utilcode.util.AppUtils


object HttpConfig {
    private var mHeaderMap: Map<String, String>? = null
    private var mParamMap = mutableMapOf(
        "v" to AppUtils.getAppVersionName(),
        "pf" to "2",
        "ak" to "F4ZG9YAQ",
        "ch" to "google",//111

    )
    private var mBaseUrl: String = ""
    private var isLog: Boolean = true

    fun init(
        baseUrl: String,
        headers: Map<String, String>? = null,
        params: Map<String, String>? = null,
        isLog: Boolean = false
    ) {
        setBaseUrl(baseUrl)
        headers?.apply { setHeader(this) }
        params?.apply { setParam(this) }
        setLogEnable(isLog)
    }

    fun setBaseUrl(url: String) {
        mBaseUrl = url
    }

    fun getBaseUrl(): String {
        return checkNotNull(mBaseUrl)
    }

    fun setHeader(map: Map<String, String>) {
        mHeaderMap = map
    }

    fun setParam(map: Map<String, String>) {
        mParamMap = map.toMutableMap()
    }

    fun getHeader(): Map<String, String>? {
        return mHeaderMap
    }

    fun getParam(): Map<String, String>? {
        return mParamMap
    }

    fun setLogEnable(isLog: Boolean) {
        HttpConfig.isLog = isLog
    }

}