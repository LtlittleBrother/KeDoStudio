package com.kedo.commonlibrary.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kedo.commonlibrary.entity.UserInfo

/**
 * @Package: com.kedo.commonlibrary.dao
 * @ClassName: UserDao
 * @Author: LiuTao
 * @CreateDate: 2/20/23 3:58 PM
 */
@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getAll(): List<UserInfo>

    @Insert
    fun addUserInfo(userInfo: UserInfo)

}