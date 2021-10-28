@file:Suppress("unused", "LocalVariableName")

package tml.libs.cku.data

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.DateFormat
import java.text.ParseException
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

class StringUtils {
    companion object {
        @Throws(ParseException::class)
        fun fromString(
            text: String,
            format: String,
            locale: Locale
        ): Calendar? {
            val cal: Calendar = Calendar.getInstance()
            val sdf = SimpleDateFormat(format, locale)
            cal.time = sdf.parse(text) // all done
            return cal
        }

        fun getString(c: Calendar, format: String, locale: Locale): String {
            val df: DateFormat = SimpleDateFormat(format, locale)
            return df.format(c.time)
        }

        fun getString(date: Date, format: String, locale: Locale): String {
            val df: DateFormat = SimpleDateFormat(format, locale)
            return df.format(date)
        }


        @Suppress("FunctionName")
        fun createDateTimeStr_yyyy_MM_dd_HH_mm_ss(): String {
            val df: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
            val c: Calendar = Calendar.getInstance()
            return df.format(c.time)
        }

        fun createDateTime(format: String, locale: Locale): String? {
            val df: DateFormat = SimpleDateFormat(format, locale)
            val c: Calendar = Calendar.getInstance()
            return df.format(c.time)
        }

        fun removeSpaceMoreThan(src: String, spaceLength: Int): String {
            var space = ""
            var result = src
            for (i in 0 until spaceLength) {
                space += " "
            }
            while (result.indexOf("$space ") > 0) {
                result = result.replace(space + " ".toRegex(), space)
            }
            return result
        }

        fun stringToDate(str: String, format: String): Date {
            val pos = ParsePosition(0)
            val df = SimpleDateFormat(format, Locale.US)
            return df.parse(str, pos)
        }

        @Suppress("MemberVisibilityCanBePrivate")
        fun getCalendar(date: Date): Calendar {
            val cal: Calendar = Calendar.getInstance(Locale.ENGLISH)
            cal.time = date
            return cal
        }

        fun getDiffYears(first: Date, last: Date): Int {
            val a: Calendar = getCalendar(first)
            val b: Calendar = getCalendar(last)
            var diff: Int = b.get(Calendar.YEAR) - a.get(Calendar.YEAR)
            if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) ||
                a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(
                    Calendar.DATE
                )
            ) {
                diff--
            }
            return abs(diff)
        }

        fun safeParseFloat(text: String): Float {
            return try {
                text.toFloat()
            } catch (ex: Exception) {
                0f
            }
        }

        fun safeParseInt(text: String): Int {
            return try {
                text.toInt()
            } catch (ex: Exception) {
                0
            }
        }

        @Throws(ParseException::class)
        fun getTimeFromString(format: String, timeStr: String): Date {
            val df = SimpleDateFormat(format, Locale.US)
            return df.parse(timeStr)
        }

        /**
         * Default First day of week is SUNDAY
         * @param yyyymmdd
         * @param weekFirstDayIsMonday
         * @return
         */
        fun getWeekOfYYYYMMDD(
            yyyymmdd: String,
            weekFirstDayIsMonday: Boolean
        ): Int {
            var locale: Locale = Locale.US // Sunday first
            if (weekFirstDayIsMonday) locale = Locale.GERMANY // Monday First
            val c: Calendar = Calendar.getInstance(locale)
            val yyyy = yyyymmdd.substring(0, 4).toInt()
            val mm = yyyymmdd.substring(4, 6).toInt()
            val dd = yyyymmdd.substring(6, 8).toInt()
            c.set(yyyy, mm - 1, dd)
            return c.get(Calendar.WEEK_OF_YEAR)
        }

        fun getWeekOfDay(c: Calendar, weekFirstDayIsMonday: Boolean): Int {
            var locale: Locale = Locale.US // Sunday first
            if (weekFirstDayIsMonday) locale = Locale.GERMANY // Monday First
            val b: Calendar = Calendar.getInstance(locale)
            b.time = c.time
            return b.get(Calendar.WEEK_OF_YEAR)
        }

        fun hashMD5(text: String): String? {
            val MD5 = "MD5"
            try {
                // Create MD5 Hash
                val digest = MessageDigest.getInstance(MD5)
                digest.update(text.toByteArray())
                val messageDigest = digest.digest()

                // Create Hex String
                val hexString = StringBuilder()
                for (aMessageDigest in messageDigest) {
                    var h = Integer.toHexString(0xFF and aMessageDigest.toInt())
                    while (h.length < 2) h = "0$h"
                    hexString.append(h)
                }
                return hexString.toString()
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            }
            return ""
        }

        fun floatToString(value: Float, useDot: Boolean): String {
            return if (useDot) ("" + value).replace(",", ".") else "" + value
        }

        fun replaceStartSpaces(replacement: String, text: String): String {
            return text.replaceFirst(" \t", replacement)
        }
    }
}