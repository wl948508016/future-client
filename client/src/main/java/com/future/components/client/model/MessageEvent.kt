package com.future.components.client.model

/**
 *
 * @Description:
 * @Author:         future
 * @CreateDate:     2022/9/6 19:03
 */
data class MessageEvent<T>(val type:String,val data: T)
