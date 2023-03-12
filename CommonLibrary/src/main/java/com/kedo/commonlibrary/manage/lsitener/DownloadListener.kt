package com.kedo.commonlibrary.manage.lsitener

import android.util.Log
import com.liulishuo.okdownload.DownloadListener
import com.liulishuo.okdownload.DownloadTask
import com.liulishuo.okdownload.core.breakpoint.BreakpointInfo
import com.liulishuo.okdownload.core.cause.EndCause
import com.liulishuo.okdownload.core.cause.ResumeFailedCause
import java.lang.Exception
import java.math.RoundingMode
import java.text.DecimalFormat

open class CommonDownloadListener : DownloadListener {

    private var mTotalContentLength: Long = 0
    private var mCurrentContentLength: Long = 0

    private val mFormat by lazy {
        DecimalFormat("#.##").apply {
            roundingMode = RoundingMode.HALF_UP
        }
    }

    companion object {
        private val TAG = "CommonDownloadListener"
    }

    override fun taskStart(task: DownloadTask) {
        mCurrentContentLength = 0
        mTotalContentLength = 0
        Log.d(TAG, "taskStart >>> task.tag == ${task.tag}")
    }

    override fun connectTrialStart(
        task: DownloadTask,
        requestHeaderFields: MutableMap<String, MutableList<String>>
    ) {
        Log.d(TAG, "connectTrialStart >>> task.tag == ${task.tag}")
    }

    override fun connectTrialEnd(
        task: DownloadTask,
        responseCode: Int,
        responseHeaderFields: MutableMap<String, MutableList<String>>
    ) {
        Log.d(TAG, "connectTrialEnd >>> task.tag == ${task.tag}   responseCode == $responseCode  ")
    }

    override fun downloadFromBeginning(
        task: DownloadTask,
        info: BreakpointInfo,
        cause: ResumeFailedCause
    ) {
        Log.d(TAG, "downloadFromBeginning >>> task.tag == ${task.tag}   cause == $cause  ")
    }

    override fun downloadFromBreakpoint(task: DownloadTask, info: BreakpointInfo) {
        Log.d(TAG, "downloadFromBreakpoint >>> task.tag == ${task.tag}")
    }

    override fun connectStart(
        task: DownloadTask,
        blockIndex: Int,
        requestHeaderFields: MutableMap<String, MutableList<String>>
    ) {
        Log.d(TAG, "connectStart >>> task.tag == ${task.tag}")
    }

    override fun connectEnd(
        task: DownloadTask,
        blockIndex: Int,
        responseCode: Int,
        responseHeaderFields: MutableMap<String, MutableList<String>>
    ) {
        Log.d(TAG, "connectEnd >>> task.tag == ${task.tag}")
    }

    override fun fetchStart(task: DownloadTask, blockIndex: Int, contentLength: Long) {
        mTotalContentLength += contentLength
    }

    override fun fetchProgress(task: DownloadTask, blockIndex: Int, increaseBytes: Long) {
        mCurrentContentLength += increaseBytes
        val progress =
            mFormat.format(mCurrentContentLength.toFloat() / mTotalContentLength.toFloat() * 100f)
                .toFloat()
        onProgress(progress)
    }

    override fun fetchEnd(task: DownloadTask, blockIndex: Int, contentLength: Long) {
        Log.d(
            TAG,
            "fetchEnd >>> task.tag == ${task.tag}  blockIndex == $blockIndex   contentLength == $contentLength"
        )
    }

    override fun taskEnd(task: DownloadTask, cause: EndCause, realCause: Exception?) {
        Log.d("liutao","task.file?.length() == ${task.file?.length()}")
        val progress =
            mFormat.format(mCurrentContentLength.toFloat() / mTotalContentLength.toFloat() * 100f)
                .toFloat()
        onProgress(progress)
    }


    protected open fun onProgress(progress: Float) {
        Log.d(
            TAG,"进度 == ${progress}%")
    }

}