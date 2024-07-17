package com.example.myhealthreminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.util.Log
import com.example.myhealthreminder.models.AlarmItemModel
import com.example.myhealthreminder.utils.AlarmReceiver
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class AndroidAlarmScheduler(private val context: Context) : AlarmScheduler {
    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    var timeString = "2024-07-16 15:35"
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm") // Adjust the pattern for your specific format

    override fun schedule(item: AlarmItemModel) {
        val intent = Intent(context, AlarmReceiver::class.java).apply { putExtra("message", item.message) }
        // get the current date and time
        val currentDateTime = LocalDateTime.now().plusSeconds(3)
//        // get date
//        val date = currentDateTime.toLocalDate()
//        // get time
//        val time = currentDateTime.toLocalTime()
//
//        timeString = "${date} ${item.time}"
        Log.d("currentDateTime", currentDateTime.toString())

//        val dateTime = LocalDateTime.parse(timeString, formatter)
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            currentDateTime.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000,
//            item.time.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000,
            PendingIntent.getBroadcast(
                context,
                item.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
        Log.d("AndroidAlarmScheduler: ", "schedule")
    }

    override fun cancel(item: AlarmItemModel) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                item.hashCode(),
                Intent(context, AlarmReceiver::class.java).apply { putExtra("message", item.message) },
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
        Log.d("AndroidAlarmScheduler: ", "cancelled")

    }
}