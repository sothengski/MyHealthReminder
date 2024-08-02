package com.example.myhealthreminder.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.myhealthreminder.MainActivity
import com.example.myhealthreminder.R

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.getStringExtra("message") ?: return
        println("Alarm triggered: $message")

        val service = NotificationService(context!!)
        service.showNotification(message)
    }
}