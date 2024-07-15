package com.example.myhealthreminder.utils

import java.text.SimpleDateFormat

fun convertTimeFormat(
    time: String,
    fromFormat: String = "HH:mm",
    toFormat: String = "hh:mm a"
): String {
    val sdf = SimpleDateFormat(fromFormat)
    val date = sdf.parse(time)
    sdf.applyPattern(toFormat)
    return sdf.format(date)
}

// convertTimeFormat(time24h, "HH:mm", "hh:mm a") // 13:15 -> 1:15 PM
// convertTimeFormat(time12h, "hh:mm a", "HH:mm") // 2:00 PM -> 14:00