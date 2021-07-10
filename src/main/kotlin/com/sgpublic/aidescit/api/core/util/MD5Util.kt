package com.sgpublic.aidescit.api.core.util

import java.security.MessageDigest

/**
 * MD5 简单封装
 */
object MD5Util {
    private val instance: MessageDigest = MessageDigest.getInstance("MD5")

    /**
     * 计算 16 位 MD5
     * @param src 传入 [String] 类型
     * @return 返回 [String]
     */
    fun encode(src: String): String {
        return encodeFull(src).substring(5, 24)
    }

    /**
     * 计算 32 位 MD5
     * @param src 传入 [String] 类型
     * @return 返回 [String]
     */
    fun encodeFull(src: String): String {
        val digest = instance.digest(src.toByteArray())
        return StringBuffer().run {
            for (b in digest) {
                val i :Int = b.toInt() and 0xff
                var hexString = Integer.toHexString(i)
                if (hexString.length < 2) {
                    hexString = "0$hexString"
                }
                append(hexString)
            }
            toString()
        }
    }
}