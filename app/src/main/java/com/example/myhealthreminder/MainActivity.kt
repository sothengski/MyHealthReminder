package com.example.myhealthreminder

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.util.Calendar
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.TextView
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
import com.example.myhealthreminder.utils.DataBaseHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {
    private var TAG: String = "MainActivity"

    private var remindersList: ArrayList<ReminderModel> = ArrayList()
    private var daysList: ArrayList<DayModel> = ArrayList()
    private var myAdapter: RemindersAdapter? = null
    private var recyclerView: RecyclerView? = null

    //    private var layoutManager: RecyclerView.LayoutManager? = null
    private var dbHelper: DataBaseHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // enable the back button in the navigation bar
//        supportActionBar!!.hide()

        // Initialize DBHelper
        dbHelper = DataBaseHelper(this, null, null, 1)

        // Get all reminders from DB
        getAllReminder()

        // Filter RecyclerView
//        filterRecyclerView()

        // Floating Action Button
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {

            // Navigate to CreateActivity
            val intent = Intent(this, CreateActivity::class.java)
            startActivity(intent)
        }
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
        val noDataText = findViewById<TextView>(R.id.noDataText)

        // Get all reminders from DB
        remindersList = dbHelper!!.getAllReminders() as ArrayList<ReminderModel>
        // reverse the list
        remindersList.reverse()

        myAdapter = RemindersAdapter(remindersList) // Initialize Adapter

        recyclerView!!.setHasFixedSize(true) // Use fixed size
        recyclerView!!.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView!!.adapter = myAdapter // Set Adapter
        // Check for data and update visibility
        if (myAdapter!!.hasData()) {
            recyclerView!!.visibility = View.VISIBLE
            noDataText.visibility = View.GONE
        } else {
            recyclerView!!.visibility = View.GONE
            noDataText.visibility = View.VISIBLE
        }
    }
}