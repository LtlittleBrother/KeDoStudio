package com.kedo.commonlibrary.ext

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore

/**
 * @Package: com.kedo.commonlibrary.ext
 * @ClassName: DataStoreExt
 * @Author: LiuTao
 * @CreateDate: 2/20/23 2:08 PM
 */

val Context.userDataStore: DataStore<Preferences> by preferencesDataStore("user_info")


suspend fun Context.saveUserInfo() {
    userDataStore.edit {

    }
}
