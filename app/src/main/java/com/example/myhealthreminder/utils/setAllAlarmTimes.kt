package com.example.myhealthreminder.utils

import com.example.myhealthreminder.AndroidAlarmScheduler
import com.example.myhealthreminder.models.AlarmItemModel
import java.util.Calendar
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.myhealthreminder.models.DayModel
import com.example.myhealthreminder.models.ReminderModel
import com.example.myhealthreminder.models.dayList

fun setAllAlarmTimes(context: Context, reminderData: ReminderModel) {
    // val DayModel List of daysSelected
    val daysSelectedList: ArrayList<DayModel> = ArrayList()
    // add days to daysSelectedList if daysSelected is not empty and contains day.title
    for (day in dayList!!) {
        if (reminderData.reminderDays.contains(day.title)) {
            daysSelectedList.add(day)
        }
    }

    var alarmItem = AlarmItemModel(
        id = reminderData.id,
        daysOfWeek = daysSelectedList,
        time = reminderData.reminderTimes,
        message = "You have a reminder for ${reminderData.title} at ${reminderData.reminderTimes}"
    )
    Log.d("setAlarm", "alarm id: ${alarmItem.id}")
    alarmItem.daysOfWeek.forEach { dayModel ->
        if (dayModel.status) {
            val calendar = Calendar.getInstance().apply {
                timeInMillis = Calendar.getInstance().timeInMillis
                // Set the time for the alarm
                set(Calendar.HOUR_OF_DAY, alarmItem.time.split(":")[0].toInt())
                set(Calendar.MINUTE, alarmItem.time.split(":")[1].toInt())
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
                // Set the day of the week for the alarm
                // get current day of week
                val currentDayOfWeek = get(Calendar.DAY_OF_WEEK)
//                Log.d("setAllAlarmTimes", "currentDayOfWeek: $currentDayOfWeek") // 1-7

                // set day of week to current day of week
                set(Calendar.DAY_OF_WEEK, dayModel.id + 1)

            }
            // Log the timeInMillis of the alarm and  System.currentTimeMillis()
//            Log.d("setAllAlarmTimes", "timeInMillis: ${calendar.timeInMillis}: ${convertMillisToCurrentDayTime(calendar.timeInMillis)}")
//            Log.d("setAllAlarmTimes", "System.currentTimeMillis(): ${System.currentTimeMillis()}: ${convertMillisToCurrentDayTime(System.currentTimeMillis())}")

            // Handle case where the calculated time is in the past
            if (calendar.timeInMillis <= System.currentTimeMillis()) {
                calendar.add(Calendar.DAY_OF_MONTH, 7) // Adjust for next week
            }
            val scheduler = AndroidAlarmScheduler(context)
            alarmItem?.let { scheduler.schedule(alarmItem, calendar.timeInMillis, alarmItem.id) }
        }
    }
}
fun cancelAllAlarmTimes(context: Context, reminderData: ReminderModel) {
   var alarmItem = AlarmItemModel(
        id = reminderData.id,
        time = reminderData.reminderTimes,
        message = "You cancel a reminder for ${reminderData.title} at ${reminderData.reminderTimes}"
    )
    val scheduler = AndroidAlarmScheduler(context)
    // Cancel the alarm
    reminderData?.let { scheduler.cancel(alarmItem) }
}

