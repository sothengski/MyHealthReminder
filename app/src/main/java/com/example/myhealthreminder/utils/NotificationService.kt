package com.example.myhealthreminder.utils

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.myhealthreminder.MainActivity
import com.example.myhealthreminder.R

class NotificationService(
    private val context: Context,
) {
    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showNotification(message: String) {
        val activityIntent = Intent(context, MainActivity::class.java)
        val activityPendingIntent = PendingIntent.getActivity(
            context,
            1,
            activityIntent,
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        )

        val noticationIntent = PendingIntent.getBroadcast(
            context,
            2,
            Intent(context, AlarmReceiver::class.java),
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        )

        val notification = NotificationCompat.Builder(context, COUNTER_CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_health_and_safety_24)
            .setContentTitle("Pill Reminder")
            .setContentText("${message}")
            .setContentIntent(activityPendingIntent)
//            .addAction(
//                R.drawable.baseline_health_and_safety_24,
//                "Remind me",
//                noticationIntent
//            )
//            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        notificationManager.notify(1, notification)
        println("showNotification")
    }

    companion object {
        const val COUNTER_CHANNEL_ID = "counter_channel"
    }
}