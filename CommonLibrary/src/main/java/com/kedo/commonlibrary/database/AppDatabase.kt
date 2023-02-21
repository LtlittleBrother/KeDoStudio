package com.kedo.commonlibrary.database

import androidx.room.Database
import com.kedo.commonlibrary.dao.UserDao
import com.kedo.commonlibrary.entity.UserInfo

/**
 * @Package: com.kedo.commonlibrary.database
 * @ClassName: AppDatabase
 * @Author: LiuTao
 * @CreateDate: 2/20/23 4:00 PM
 */
@Database(entities = [UserInfo::class], version = 1)
abstract class AppDatabase {
    abstract fun userDao(): UserDao
}