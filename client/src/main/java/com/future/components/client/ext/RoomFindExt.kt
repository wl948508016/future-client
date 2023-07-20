package com.future.components.client.ext

import androidx.lifecycle.viewModelScope
import com.future.components.client.utils.LogUtils
import com.future.components.client.viewmodel.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 *
 * @Description:
 * @Author:         future
 * @CreateDate:     2022/8/24 09:05
 */
fun <T> BaseViewModel.find(
    block: suspend () -> T,
    success:(T) -> Unit = {},
    error: (Throwable) -> Unit = {},
): Job {
    return viewModelScope.launch {
        runCatching {
            block()
        }.onSuccess {
            success(it)
        }.onFailure {
            //打印错误消息
            LogUtils.e(it.message)
            error(it)
        }
    }
}