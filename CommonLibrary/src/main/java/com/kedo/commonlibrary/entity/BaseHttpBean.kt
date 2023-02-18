package com.kedo.commonlibrary.entity

/**
 * @Package: com.blank.commonlibrary.entity
 * @ClassName: BaseHttpBean
 * @Author: LiuTao
 * @CreateDate: 2022/8/22 15:49
 */
open class BaseHttpBean<T>(
    open var code: Int = -1,
    open var msg: String? = null,
    open var data: T? = null
)


enum class State {
    Success, Error
}