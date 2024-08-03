package com.example.myhealthreminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.util.Log
import com.example.myhealthreminder.models.AlarmItemModel
import com.example.myhealthreminder.utils.AlarmReceiver
import com.example.myhealthreminder.utils.convertMillisToCurrentDayTime
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class AndroidAlarmScheduler(private val context: Context) : AlarmScheduler {
    private val alarmManager = context.getSystemService(AlarmManager::class.java)

//    var timeString = "2024-07-16 15:35"
//    val formatter =
//        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm") // Adjust the pattern for your specific format

    override fun schedule(item: AlarmItemModel, triggerTime: Long, alarmId: Int) {
        val intent =
            Intent(context, AlarmReceiver::class.java).apply { putExtra("message", item.message) }
        // get the current date and time
        val currentDateTime = LocalDateTime.now().plusSeconds(3)
//        // get date
//        val date = currentDateTime.toLocalDate()
//        // get time
//        val time = currentDateTime.toLocalTime()

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerTime,
            PendingIntent.getBroadcast(
                context,
                item.id,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
        Log.d(
            "AndroidAlarmScheduler",
            "Alarm scheduled for ID: ${item.id}, Trigger Time: $triggerTime: ${
                convertMillisToCurrentDayTime(triggerTime)
            }"
        )
    }

    override fun cancel(item: AlarmItemModel) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                item.id,
                Intent(context, AlarmReceiver::class.java).apply {
                    putExtra(
                        "message",
                        item.message
                    )
                },
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
        Log.d("AndroidAlarmScheduler", "Alarm cancel for ID: ${item.id}")
    }
}