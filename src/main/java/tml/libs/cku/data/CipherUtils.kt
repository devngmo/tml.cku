@file:Suppress("unused")

package tml.libs.cku.data

import tml.libs.cku.io.StaticLogger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class CipherUtils {
    companion object {
        const val TAG = "CipherUtils"

        fun md5(s: String): String? {
            try {
                // Create MD5 Hash
                val digest = MessageDigest.getInstance("MD5")
                digest.update(s.toByteArray())
                val messageDigest = digest.digest()

                // Create Hex String
                val hexString = StringBuffer()
                for (i in messageDigest.indices) hexString.append(
                    Integer.toHexString(
                        0xFF and messageDigest[i].toInt()
                    )
                )
                return hexString.toString()
            } catch (e: NoSuchAlgorithmException) {
                StaticLogger.E(TAG, "md5", e)
            }
            return ""
        }
    }
}