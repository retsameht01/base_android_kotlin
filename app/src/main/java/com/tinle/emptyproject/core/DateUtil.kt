package com.tinle.emptyproject.core

import java.text.SimpleDateFormat
import java.util.*

class DateUtil {
    private val DATEFORMAT:String = "MM/dd/yyyy"
    fun getUTCdateAsString(): String {
        val sdf = SimpleDateFormat(DATEFORMAT)
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        return sdf.format(Date())
    }
    private val DATETIMEFORMAT:String = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
    fun getUTCDateTimestamp():String{
        val sdf = SimpleDateFormat(DATETIMEFORMAT)
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        return sdf.format(Date())
    }


    fun findDaysDiff(startDate: String): Long {
        val unixStartTime = getTimeMillis(startDate)
        val calendar1 = Calendar.getInstance()
        calendar1.timeInMillis = unixStartTime
        calendar1.set(Calendar.HOUR_OF_DAY, 0)
        calendar1.set(Calendar.MINUTE, 0)
        calendar1.set(Calendar.SECOND, 0)
        calendar1.set(Calendar.MILLISECOND, 0)

        val calendar2 = Calendar.getInstance()
        calendar2.timeInMillis = System.currentTimeMillis()
        calendar2.set(Calendar.HOUR_OF_DAY, 0)
        calendar2.set(Calendar.MINUTE, 0)
        calendar2.set(Calendar.SECOND, 0)
        calendar2.set(Calendar.MILLISECOND, 0)

        return ((calendar2.timeInMillis - calendar1.timeInMillis) / (24 * 60 * 60 * 1000))
    }

    fun getTimeMillis(dateString:String):Long {

        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val date = sdf.parse(dateString)
        val millis = date.time
        return millis
    }
}
