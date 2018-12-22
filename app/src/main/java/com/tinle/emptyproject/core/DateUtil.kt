package com.tinle.emptyproject.core

import java.text.SimpleDateFormat
import java.util.*

class DateUtil {
    private val DATEFORMAT:String = "MM/dd/yyyy"
    fun getUTCdatetimeAsString(): String {
        val sdf = SimpleDateFormat(DATEFORMAT)
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        return sdf.format(Date())
    }
}
