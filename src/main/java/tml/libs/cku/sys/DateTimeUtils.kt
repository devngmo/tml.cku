@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package tml.libs.cku.sys

class DateTimeUtils {
    companion object {
        const val TICKS_AT_EPOCH : Long = 621355968000000000
        const val TICKS_PER_MILLISECOND : Long = 10000

        fun millisecToTicks(javaDateMillisec: Long):Long {
            val v : Long = javaDateMillisec
            //if (v > 0) v = -v
            val ticks = TICKS_AT_EPOCH + v * TICKS_PER_MILLISECOND
            return ticks
        }

        fun ticksToMillsec(ticks: Long):Long {
            return (ticks - TICKS_AT_EPOCH) / TICKS_PER_MILLISECOND
        }
    }
}