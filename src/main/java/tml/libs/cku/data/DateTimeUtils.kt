package tml.libs.cku.data

import java.util.*

class DateTimeUtils {
    companion object {
        fun addHoursToJavaUtilDate(date: Date?, hours: Int): Date {
            val calendar: Calendar = Calendar.getInstance()
            calendar.time = date
            calendar.add(Calendar.HOUR_OF_DAY, hours)
            return calendar.time
        }

        fun todayYYYYMMDD(): String {
            return StringUtils.getString(Date(), "yyyy-MM-dd", Locale.ENGLISH)
        }
    }
}