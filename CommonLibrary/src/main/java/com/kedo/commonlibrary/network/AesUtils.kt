package com.kedo.commonlibrary.network

import android.util.Base64
import com.blankj.utilcode.util.EncryptUtils
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object AesUtils {
    /**
     * 算法/模式/填充
     */
    private const val CipherMode = "AES/CBC/PKCS5Padding"
    private const val key_ = "UcLAFLxsZidSX0wM"
    private const val iv = "1016449182158477"
    /**
     * 创建密钥
     */
    private fun createKey(key: String): SecretKeySpec {
        var key: String? = key
        var data: ByteArray? = null
        if (key == null) {
            key = ""
        }
        val sb = StringBuffer(16)
        sb.append(key)
        while (sb.length < 16) {
            sb.insert(0," ")
        }
        if (sb.length > 16) {
            sb.setLength(16)
        }
        try {
            data = sb.toString().toByteArray(Charset.forName("utf-8"))
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
        return SecretKeySpec(data, "AES")
    }

    private fun createIV(password: String): IvParameterSpec {
        var password: String? = password
        var data: ByteArray? = null
        if (password == null) {
            password = ""
        }
        val sb = StringBuffer(16)
        sb.append(password)
        while (sb.length < 16) {
            sb.append("0")
        }
        if (sb.length > 16) {
            sb.setLength(16)
        }
        try {
            data = sb.toString().toByteArray()
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
        return IvParameterSpec(data)
    }

    /**
     * 加密字节数据
     */
    private fun encrypt(content: ByteArray?): ByteArray? {
        try {
            val key = createKey(key_)
            val cipher = Cipher.getInstance(CipherMode)
            cipher.init(Cipher.ENCRYPT_MODE, key, createIV(iv))
            return cipher.doFinal(content)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 加密(结果为16进制字符串)
     */
    fun encrypt(content: String): String {
        var data: ByteArray? = null
        try {
            data = content.toByteArray()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        data = encrypt(data)
        return Base64.encodeToString(data,Base64.NO_WRAP)
    }

    /**
     * content: 解密内容(base64编码格式)
     * slatKey: 加密时使用的盐，16位字符串
     * vectorKey: 加密时使用的向量，16位字符串
     */
    fun decrypt(content: String?): String? {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val secretKey: SecretKey = SecretKeySpec(key_.toByteArray(), "AES")
        val iv = IvParameterSpec(iv.toByteArray())
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv)
        val encrypted = cipher.doFinal(content?.toByteArray())
        return String(encrypted)
    }

    /**
     * 解密字节数组
     */
    fun decrypt(content: ByteArray?): ByteArray? {
        try {
            return EncryptUtils.decryptHexStringAES(
                String(content ?: byteArrayOf()), key_.toByteArray(),
                CipherMode, iv.toByteArray()
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

//    /**
//     * 解密(输出结果为字符串)
//     */
//    fun decrypt(content: String): String? {
//        var data: ByteArray? = null
//        try {
//            data = hex2byte(content)
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//        data = decrypt(data)
//        if (data == null) return null
//        var result: String? = null
//        try {
//            result = String(data, Charset.forName("UTF-8"))
//        } catch (e: UnsupportedEncodingException) {
//            e.printStackTrace()
//        }
//        return result
//    }
//
//    /**
//     * 字节数组转成16进制字符串
//     */
//    fun byte2hex(b: ByteArray?): String { // 一个字节的数，
//        val sb = StringBuffer(b!!.size * 2)
//        var tmp = ""
//        for (n in b.indices) { // 整数转成十六进制表示
//            tmp = Integer.toHexString(b[n].toInt() and 0XFF)
//            if (tmp.length == 1) {
//                sb.append("0")
//            }
//            sb.append(tmp)
//        }
//        return sb.toString().toUpperCase() // 转成大写
//    }

//    /**
//     * 将hex字符串转换成字节数组
//     */
//    private fun hex2byte(inputString: String): ByteArray {
//        var inputString: String? = inputString
//        if (inputString == null || inputString.length < 2) {
//            return ByteArray(0)
//        }
//        inputString = inputString.toLowerCase()
//        val l = inputString.length / 2
//        val result = ByteArray(l)
//        for (i in 0 until l) {
//            val tmp = inputString.substring(2 * i, 2 * i + 2)
//            result[i] = (tmp.toInt(16) and 0xFF).toByte()
//        }
//        return result
//    }
}