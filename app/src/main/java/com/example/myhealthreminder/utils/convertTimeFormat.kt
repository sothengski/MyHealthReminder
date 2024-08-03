package com.example.myhealthreminder.utils

import android.content.Context
import java.text.SimpleDateFormat
import java.util.Calendar

fun convertTimeFormat(
    time: String,
//    fromFormat: String = "HH:mm",
//    toFormat: String = "hh:mm a",
    is24h: Boolean = true
): String {
    val fromFormat = if (is24h) "HH:mm" else "hh:mm a" // 13:15 -> 1:15 PM
    val toFormat = if (is24h) "hh:mm a" else "HH:mm" // 1:15 PM -> 13:15

    val sdf = SimpleDateFormat(fromFormat)
    val date = sdf.parse(time)
    sdf.applyPattern(toFormat)
    return sdf.format(date)
}


fun convertMillisToCurrentDayTime(millis: Long): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = millis

    // Format the date and time as desired
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    return dateFormat.format(calendar.time)
}

fun isDeviceIn24HourFormat(context: Context): Boolean {
    return android.text.format.DateFormat.is24HourFormat(context)
}

// convertTimeFormat(time24h, "HH:mm", "hh:mm a") // 13:15 -> 1:15 PM
// convertTimeFormat(time12h, "hh:mm a", "HH:mm") // 2:00 PM -> 14:00