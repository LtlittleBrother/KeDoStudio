package com.kedo.app.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ModelData(
    //试穿语音
    val audioUrl: String? = null,

    val background: ModelBg? = null,
    //背景图id
    val backgroundId: Long = 0,
    //模型创建时间
    val createAt: String? = null,
    //折扣显示
    val discount: String? = null,
    //收集图鉴图
    val gatherUrl: String? = null,
    //物品预览图
    val goodsUrl: String? = null,
    //模型id
    val id: Int = 0,
    //模型预览图
    val imgUrl: String? = null,
    val isDel: Int = 0,
    //显示状态：-1关闭，1开启
    val isShow: Int = 0,
    //正式模型文件地址
    val modelOneUrl: String? = null,
    //正式模型版本号
    val modelOneVersion: String? = null,
    //内测模型文件地址
    val modelTwoUrl: String? = null,
    //内测模型版本号
    val modelTwoVersion: String? = null,
    val motionId: Int = 0,
    //模型描述
    val ms: String? = null,
    //模型名称
    val name: String? = null,
    //金币数量
    val number: String? = null,
    //付费模式:免费1,VIP2,钻石3,签到4,活动5
    val payType: String? = null,
    //排序：倒序
    val sort: Int = 0,
    //状态：-1关闭，1开启，2内测
    val status: Int = 0,
    //模型类型id:通用模型1,梦套装模型2
    val type: String? = null,
    val updateAt: String? = null,
) : Parcelable

@Parcelize
data class ModelBg(
    val categoryId: Int = 0,
    val createAt: String? = null,
    //默认图
    val defaultImgUrl: String? = null,
    //资源包
    val fileUrl: String? = null,
    //品质
    val grade: String? = null,
    //背景ID
    val id: Int = 0,
    //预览图
    val imgUrl: String? = null,
    //显示状态：-1隐藏，1显示
    val isDel: Int = 0,
    //背景描述
    val isShow: Int = 0,
    //背景名称
    val ms: String? = null,
    //背景名称
    val name: String? = null,
    //背景付费模式:免费1,VIP2,金币购买3,套装4,天气系统5,创意工坊6
    val payType: String? = null,
    val sort: Int = 0,
    //状态：-1关闭，1开启
    val status: Int = 0,
    //背景类型:普通背景1,动态背景2,天气背景3
    val type: String? = null,
    val updateAt: String? = null,
    //版本号
    val version: String? = null
) : Parcelable
