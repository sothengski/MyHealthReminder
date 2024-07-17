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

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.getStringExtra("message") ?: return
        println("Alarm triggered: $message")

        val service = NotificationService(context!!)
        service.showNotification(message)


//        val i = Intent(context, MainActivity::class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        val pendingIntent = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_IMMUTABLE)
////        val alarmSound = Uri.parse("android.resource://" + context!!.packageName + "/" + R.raw.alarm)
//        val builder = NotificationCompat.Builder(context!!, "MyHealthReminder")
//            .setSmallIcon(R.drawable.pill_symbol)
//            .setContentTitle("My Health Reminder Alarm Manager")
//            .setContentText("Take your pills")
//            .setAutoCancel(true)
//            .setOngoing(false)
//            .setOnlyAlertOnce(true)
//            .setDefaults(NotificationCompat.DEFAULT_ALL)
//            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .setContentIntent(pendingIntent)
//
//        val notificationManager = NotificationManagerCompat.from(context)
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
////            builder.setChannelId("MyHealthReminder")
////            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManagerCompat
////            val channel = NotificationChannel("MyHealthReminder", "MyHealthReminder", NotificationManager.IMPORTANCE_HIGH)
////            notificationManager.createNotificationChannel(channel)
//            val channelId = "Channel_id"
//            val channel = NotificationChannel(channelId, "MyHealthReminder", NotificationManager.IMPORTANCE_HIGH)
//            channel.enableVibration(true)
//            channel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
//            notificationManager!!.createNotificationChannel(channel)
//            builder.setChannelId(channelId)
//        }

//        if (ActivityCompat.checkSelfPermission(
//               this,
//                Manifest.permission.POST_NOTIFICATIONS
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return
//        }

//        val notificationManager = NotificationManagerCompat.from(context)

//        notificationManager!!.notify(123, builder!!.build())
    }
}