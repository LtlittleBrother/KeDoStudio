package com.kedo.commonlibrary.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @Package: com.kedo.commonlibrary.entity
 * @ClassName: UserInfo
 * @Author: LiuTao
 * @CreateDate: 2/20/23 3:55 PM
 */
@Entity
data class UserInfo(
    @PrimaryKey
    val userId: Int = 0,
    @ColumnInfo("user_name")
    val userName: String? = null,
    @ColumnInfo("user_avatar")
    val avatar: String? = null,
    @ColumnInfo("user_phone")
    val phone: String? = null,
)
