package com.kedo.commonlibrary.state

/**
 * @Package: com.kedo.commonlibrary.state
 * @ClassName: BaseUiState
 * @Author: LiuTao
 * @CreateDate: 2/16/23 5:31 PM
 */
data class BaseUiState<T>(
    val showLoading: Boolean = false,
    val showError: Boolean = false,
    val showEmpty: Boolean = false,
    val hideLoading: Boolean = false,
    val uiState: T? = null
)
