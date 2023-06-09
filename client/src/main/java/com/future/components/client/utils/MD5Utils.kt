package com.future.components.client.utils

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

/**
 *
 * @Description:
 * @Author:         future
 * @CreateDate:     2022/10/26 10:29
 */
object MD5Utils {
    // md5加密 32位小写
    fun md5(sourceStr: String): String {
        var result = ""
        try {
            val md: MessageDigest = MessageDigest.getInstance("MD5")
            md.update(sourceStr.toByteArray())
            val b: ByteArray = md.digest()
            var i: Int
            val buf = StringBuffer("")
            for (offset in b.indices) {
                i = b[offset].toInt()
                if (i < 0) i += 256
                if (i < 16) buf.append("0")
                buf.append(Integer.toHexString(i))
            }
//            result = buf.toString().substring(8, 24).uppercase(Locale.getDefault())
            result = buf.toString().lowercase(Locale.getDefault())
//            LogUtils.e("result: $result") //32位的加密
//            LogUtils.e("result: " + buf.toString().substring(8, 24)) //16位的加密
        } catch (e: NoSuchAlgorithmException) {
            //
        }
        return result
    }
}