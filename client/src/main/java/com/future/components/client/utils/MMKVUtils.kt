package com.future.components.client.utils

import android.text.TextUtils
import com.tencent.mmkv.MMKV

/**
 *
 * @Description:
 * @Author:         future
 * @CreateDate:     2021/11/4 2:37 下午
 */
private fun getMMKV(): MMKV? {
    return MMKV.defaultMMKV()
}

private fun isNull(): Boolean {
    return TextUtils.isEmpty(MMKV.getRootDir())
}

fun pushBoolean(key: String, value: Boolean) {
    if (!isNull()) {
        getMMKV()?.apply {
            this.encode(key, value)
        }
    }
}

fun getBoolean(key: String, default: Boolean = false): Boolean {
    return if (isNull()) {
        default
    } else {
        getMMKV().run {
            // 关键修复：decodeBool 单参版本在 key 不存在时返回 false 而非 null，
            // ?: default 永远不触发；必须把 default 传给双参重载让 MMKV 兜底
            this?.decodeBool(key, default) ?: default
        }
    }
}

fun pushString(key: String, value: String) {
    if (!isNull()) {
        getMMKV()?.apply {
            this.encode(key, value)
        }
    }
}

fun getString(key: String, default: String = ""): String {
    return if (isNull()) {
        default
    } else {
        getMMKV().run {
            this?.decodeString(key, default) ?: default
        }
    }
}

fun pushInt(key: String, value: Int) {
    if (!isNull()) {
        getMMKV()?.apply {
            this.encode(key, value)
        }
    }
}

fun getInt(key: String, default: Int = -1): Int {
    return if (isNull()) {
        default
    } else {
        getMMKV().run {
            // 关键修复：decodeInt 单参版本在 key 不存在时返回 0 而非 null，
            // ?: default 永远不触发（这就是 getCameraType() 拿到 0 的原因）
            this?.decodeInt(key, default) ?: default
        }
    }
}