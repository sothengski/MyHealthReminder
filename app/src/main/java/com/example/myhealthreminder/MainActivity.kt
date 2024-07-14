package com.example.myhealthreminder

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myhealthreminder.adapters.DaysAdapter
import com.example.myhealthreminder.adapters.RemindersAdapter
import com.example.myhealthreminder.models.DayModel
import com.example.myhealthreminder.models.ReminderModel
import com.example.myhealthreminder.utils.DataBaseHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton

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
        supportActionBar!!.hide()

        // Initialize DBHelper
        dbHelper = DataBaseHelper(this, null, null, 1)

        // Get all reminders from DB
        getAllReminder()

        // Filter RecyclerView
        filterRecyclerView()
//        reminderRecyclerView()

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
}