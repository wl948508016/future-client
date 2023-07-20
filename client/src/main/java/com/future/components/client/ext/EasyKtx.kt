package com.future.components.client.ext

/**
 *
 * @Description:
 * @Author:         future
 * @CreateDate:     2023/5/18 11:42
 */
inline fun <T1: Any, T2: Any, T3: Any> ifLet(p1: T1?, p2: T2?, p3: T3?, closure: (T1, T2, T3) -> Unit) {
    if (p1 != null && p2 != null && p3 !=null) closure(p1,p2,p3)
}

inline fun <T1: Any, T2: Any> ifLet(p1: T1?, p2: T2?, closure: (T1, T2) -> Unit) {
    if (p1 != null && p2 != null) closure(p1,p2)
}

fun <T1: Any, T2: Any, R: Any> safeLet(p1: T1?, p2: T2?, block: (T1, T2)->R?): R? {
    return if (p1 != null && p2 != null) block(p1, p2) else null
}

// 3 variables
fun <T1: Any, T2: Any, T3: Any, R: Any> safeLet(p1: T1?, p2: T2?, p3: T3?, block: (T1, T2, T3)->R?): R? {
    return if (p1 != null && p2 != null && p3 != null) block(p1, p2, p3) else null
}