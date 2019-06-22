package com.tinle.checkin.core

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class EncryptionService: IEncryption {
    override fun encryptHmacSha1(key:String, text:String):String {
        return getEncryption(key, text)
    }

    private fun getEncryption(key:String, text:String):String {
        var keySpec = SecretKeySpec(key.toByteArray(Charsets.UTF_8), "HmacSHA1")
        val mac = Mac.getInstance("HmacSHA1")
        mac.init(keySpec)
        val bytes = mac.doFinal(text.toByteArray(Charsets.UTF_8))
        return android.util.Base64.encodeToString(bytes, android.util.Base64.DEFAULT)
    }
}

interface IEncryption{
    fun encryptHmacSha1(key:String, text:String):String
}