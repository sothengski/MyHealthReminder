package com.example.myhealthreminder

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myhealthreminder.adapters.DaysAdapter
import com.example.myhealthreminder.adapters.RemindersAdapter
import com.example.myhealthreminder.models.AlarmItemModel
import com.example.myhealthreminder.models.DayModel
import com.example.myhealthreminder.models.ReminderModel
import com.example.myhealthreminder.utils.AlarmReceiver
import com.example.myhealthreminder.utils.DataBaseHelper
import com.example.myhealthreminder.utils.NotificationService
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {
    private var TAG: String = "MainActivity"

    private var remindersList: ArrayList<ReminderModel> = ArrayList()
    private var daysList: ArrayList<DayModel> = ArrayList()
    private var myAdapter: RemindersAdapter? = null
    private var recyclerView: RecyclerView? = null

    //    private var layoutManager: RecyclerView.LayoutManager? = null
    private var dbHelper: DataBaseHelper? = null

    private lateinit var calendar: Calendar
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    var permission: Array<String> = arrayOf(
        "Manifest.permission.POST_NOTIFICATIONS",
    )

    var permissionPostNotification: Boolean = false;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Notification Channel
//        createNotificationChannel()

//        if (!permissionPostNotification) {
//            requestPermissionNotification()
//        } else {
//            Toast.makeText(this, "Notification Permission Granted.", Toast.LENGTH_SHORT).show()
//        }

        val service = NotificationService(applicationContext)

        // enable the back button in the navigation bar
        supportActionBar!!.hide()

        // Initialize DBHelper
        dbHelper = DataBaseHelper(this, null, null, 1)

        calendar = Calendar.getInstance()

//        val scheduler = AndroidAlarmScheduler(this)
//        var alarmItem: AlarmItemModel? = null

        // Get all reminders from DB
        getAllReminder()

        // Filter RecyclerView
        filterRecyclerView()
        setAlarm()
//        reminderRecyclerView()

//        calendar.set(Calendar.HOUR_OF_DAY, 16)
//        calendar.add(Calendar.MINUTE, 1)
////        calendar.set(Calendar.MINUTE,LocalDateTime.now().plusSeconds(30).minute)
//        calendar.set(Calendar.SECOND, 0)
//        // print calendar time in hour:minute:second format
//        println("get calendar time: ${calendar.time}")
//        println("get calendar timeInMillis: ${calendar.timeInMillis}")

        // Floating Action Button
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            service.showNotification("My Notification")
//            alarmItem?.let (scheduler::cancel)
//            cancelAlarm()

//            calendar.set(Calendar.HOUR_OF_DAY, 16)
//            calendar.add(Calendar.MINUTE, 1)
////            calendar.set(Calendar.MINUTE,LocalDateTime.now().plusSeconds(30).minute)
//            calendar.set(Calendar.SECOND, 0)
//            println("get calendar time: ${calendar.time}")
//            println("get calendar timeInMillis: ${calendar.timeInMillis}")
//            setAlarm()
            // Navigate to CreateActivity
            val intent = Intent(this, CreateActivity::class.java)
            startActivity(intent)
        }
    }

    private fun requestPermissionNotification() {
        if (ContextCompat.checkSelfPermission(
                this,
                permission[0]
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            permissionPostNotification = true
        } else {
            if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                Toast.makeText(
                    this,
                    "Permission inside else first time don't allow",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this,
                    "Permission inside else second time don't allow",
                    Toast.LENGTH_SHORT
                ).show()
//                requestPermissionLauncherNotification.launch(permission[0])
            }

        }
    }

    private val requestPermissionLauncherNotification =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                permissionPostNotification = true
                Toast.makeText(this, "Notification Permission Granted.", Toast.LENGTH_SHORT).show()
            } else {
                permissionPostNotification = false
                Toast.makeText(this, "Notification Permission Denied.", Toast.LENGTH_SHORT).show()
                showPermissionDialog("Notification Permission");
//                Snackbar.make(
//                    viewBinding.containermain,
//                    String.format(
//                        String.format(
//                            getString(R.string.txt_error_post_notification),
//                            getString(R.string.app_name)
//                        )
//                    ),
//                    Snackbar.LENGTH_INDEFINITE
//                ).setAction(getString(R.string.goto_settings)) {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                        val settingsIntent: Intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
//                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                            .putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
//                        startActivity(settingsIntent)
//                    }
//                }.show()
            }
        }

    private fun showPermissionDialog(s: String) {
        AlertDialog.Builder(this).setTitle("Alert for Permission")
            .setPositiveButton("Go to Settings") { dialog, which ->
                var rintent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                var uri = android.net.Uri.fromParts("package", packageName, null)
                rintent.data = uri
                startActivity(rintent)
                dialog.dismiss()

            }
            .setNegativeButton("Exit") { dialog, which ->
                dialog.dismiss()
            }.show()


    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        if (myAdapter != null) {
            getAllReminder()
            myAdapter!!.notifyDataSetChanged()
        }
    }

    private fun getAllReminder() {
        recyclerView = findViewById(R.id.recyclerView)

        // Get all reminders from DB
        remindersList = dbHelper!!.getAllReminders() as ArrayList<ReminderModel>
        // reverse the list
        remindersList.reverse()

        myAdapter = RemindersAdapter(remindersList) // Initialize Adapter

        recyclerView!!.setHasFixedSize(true) // Use fixed size
        recyclerView!!.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView!!.adapter = myAdapter // Set Adapter
    }

    private fun filterRecyclerView() {
        // 1- RecyclerView
        val filterRecyclerView: RecyclerView = findViewById(R.id.recyclerView_filter)
        filterRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // 2- Data source: List of ReminderModel Objects
        val filtersList: ArrayList<DayModel> = ArrayList()

        val filter1 = DayModel(1, "Mon", true)
        val filter2 = DayModel(2, "Tue", false)
        val filter3 = DayModel(3, "Wed", false)
        val filter4 = DayModel(4, "Thu", false)
        val filter5 = DayModel(5, "Fri", false)
        val filter6 = DayModel(6, "Sat", false)
        val filter7 = DayModel(7, "Sun", false)


        filtersList.add(filter1)
        filtersList.add(filter2)
        filtersList.add(filter3)
        filtersList.add(filter4)
        filtersList.add(filter5)
        filtersList.add(filter6)
        filtersList.add(filter7)

        // 3- Adapter
        val adapter = DaysAdapter(filtersList)
        filterRecyclerView.adapter = adapter
    }


    private fun setAlarm() {
        val currentDateTime = LocalDateTime.now()
        // get date
        val date = currentDateTime.toLocalDate()
        // get time
//        val time = currentDateTime.toLocalTime()

        var timeString = "${date}"

        calendar.set(Calendar.HOUR_OF_DAY, 16)
        calendar.add(Calendar.MINUTE, 1)
        calendar.set(Calendar.SECOND, 0)

//        println("get calendar time: ${calendar.time}")
//        println("get calendar timeInMillis: ${calendar.timeInMillis}")

        //add calendar time into timeString
        timeString = "${date} ${calendar.time}"

//        Log.d("timeString: ", timeString)
        val scheduler = AndroidAlarmScheduler(this)
        var alarmItem = AlarmItemModel(time = timeString, message = "My Alarm triggering now")
        alarmItem?.let { scheduler.schedule(it) }

//        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
//        val intent = Intent(this, AlarmReceiver::class.java)
//
//        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
//
////        alarmManager.setRepeating(
////            AlarmManager.RTC_WAKEUP,
////            calendar.timeInMillis,
////            AlarmManager.INTERVAL_DAY,
////            pendingIntent
////        )
//        alarmManager.setExactAndAllowWhileIdle(
//            AlarmManager.RTC_WAKEUP,
//            calendar.timeInMillis,
//            pendingIntent
//        )
//        println("set Alarm calendar time: ${calendar.timeInMillis}")

        Toast.makeText(this, "Alarm set successfully", Toast.LENGTH_SHORT).show()
    }

    private fun cancelAlarm() {
        val currentDateTime = LocalDateTime.now()
        // get date
        val date = currentDateTime.toLocalDate()
        // get time
//        val time = currentDateTime.toLocalTime()

        var timeString = "${date}"

        calendar.set(Calendar.HOUR_OF_DAY, 16)
        calendar.add(Calendar.MINUTE, 1)
        calendar.set(Calendar.SECOND, 0)

//        println("get calendar time: ${calendar.time}")
//        println("get calendar timeInMillis: ${calendar.timeInMillis}")

        //add calendar time into timeString
        timeString = "${date} ${calendar.time}"
        val scheduler = AndroidAlarmScheduler(this)
        var alarmItem = AlarmItemModel(time = timeString, message = "My Alarm triggering now")
        alarmItem?.let { scheduler.cancel(it) }
//        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
//        val intent = Intent(this, AlarmReceiver::class.java)
//
//        pendingIntent = PendingIntent.getBroadcast(
//            this,
//            0,
//            intent,
//            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//        )
//        alarmManager.cancel(pendingIntent)
//        pendingIntent.cancel()
        Toast.makeText(this, "Alarm cancelled", Toast.LENGTH_SHORT).show()
    }

}