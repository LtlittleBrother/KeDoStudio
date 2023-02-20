package com.kedo.commonlibrary.ext

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

/**
 * @Package: com.kedo.commonlibrary.ext
 * @ClassName: DataStoreExt
 * @Author: LiuTao
 * @CreateDate: 2/20/23 2:08 PM
 */

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("common_data_store")

val EXAMPLE_COUNTER = intPreferencesKey("example_counter")


suspend fun Context.saveCount(value: Int) {
    dataStore.edit {
        it[EXAMPLE_COUNTER] = value
    }
}

suspend fun Context.getCount(): Int {
    val value = dataStore.data.map {
        it[EXAMPLE_COUNTER]
    }.firstOrNull()
    return value ?: 0
}