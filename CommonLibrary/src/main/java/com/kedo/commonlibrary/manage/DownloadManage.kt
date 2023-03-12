package com.kedo.commonlibrary.manage

import com.kedo.commonlibrary.ext.appContext
import com.kedo.commonlibrary.manage.lsitener.CommonDownloadListener
import com.liulishuo.okdownload.DownloadTask
import java.io.File

var defaultPath = appContext.filesDir.absolutePath + "/live2d/"

class DownloadManage {

    private val mLiveFileParent by lazy {
        File(defaultPath)
    }

    companion object {
        fun instant() = DownloadManageHolder.holder
    }

    private object DownloadManageHolder {
        val holder = DownloadManage()
    }

    private val mTaskList = arrayListOf<DownloadTask>()

    /**
     * 任务
     * @param passIfAlreadyCompleted 是否强制重新下载
     * */
    fun startSingleDownloadTask(downloadUrl: String, fileName: String,passIfAlreadyCompleted: Boolean = false,downloadListener: CommonDownloadListener){
        val task = DownloadTask.Builder(downloadUrl, mLiveFileParent)
            .setMinIntervalMillisCallbackProcess(30)
            .setPassIfAlreadyCompleted(passIfAlreadyCompleted)
            .setAutoCallbackToUIThread(true)
            .setFilename(fileName)
            .build()

        task.tag = downloadUrl
        task.enqueue(downloadListener)
        mTaskList.add(task)
    }

    //取消下载任务
    fun cancelDownloadTask(url: String){
        val task = mTaskList.find { it.tag == url }
        task?.cancel()
        mTaskList.remove(task)
    }

}